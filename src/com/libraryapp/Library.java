package com.libraryapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Library {

    private final List<LibraryItem> items;
    private final List<LoanRecord> loans;
    private final List<LibraryUser> users;

    public Library(List<LibraryItem> items,
            List<LoanRecord> loans,
            List<LibraryUser> users) {

        this.items = new ArrayList<>(Objects.requireNonNull(items));
        this.loans = new ArrayList<>(Objects.requireNonNull(loans));
        this.users = new ArrayList<>(Objects.requireNonNull(users));
    }

    public void addItems(LibraryItem... newItems) {
        for (LibraryItem item : newItems) {
            this.items.add(item);
        }
    }

    public void addUser(LibraryUser user) {
        this.users.add(user);
    }

    public void printAllItems() {
        items.forEach(item -> System.out.println(formatItem(item)));
    }

    public void printMatchingItems(Predicate<LibraryItem> predicate) {
        items.stream()
                .filter(predicate)
                .map(this::formatItem) // method reference to instance method
                .forEach(System.out::println);
    }

    public void borrowBook(String memberId, Isbn isbn, LocalDate borrowDate)
            throws LibraryException {

        LibraryUser user = findUserById(memberId);
        LibraryItem item = findItemByIsbn(isbn);

        if (!(item instanceof BookItem book)) {
            throw new LibraryException("Only books can be borrowed with this operation.");
        }

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is already borrowed.");
        }

        if (user instanceof AbstractMember member) {
            int loanId = loans.size() + 1;

            LocalDate dueDate = book.calculateDueDate(borrowDate);

            LoanRecord record = new LoanRecord(loanId, isbn, memberId, borrowDate, dueDate);
            loans.add(record);

            book.setAvailable(false);
        } else {
            throw new LibraryException("Only members can borrow books.");
        }
    }

    public void returnBook(String memberId, Isbn isbn, LocalDate returnDate)
            throws LibraryException {

        LoanRecord recordToClose = loans.stream()
                .filter(r -> r.isbn().equals(isbn) && r.memberId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new LibraryException("No loan found for this member and book."));

        LibraryItem item = findItemByIsbn(isbn);
        if (item instanceof BookItem book) {
            book.setAvailable(true);
        }

        boolean overdue = Borrowable.isOverdue(recordToClose.dueDate(), returnDate);
        if (overdue) {
            System.out.println("Warning: this book is overdue!");
        }

        loans.remove(recordToClose);
    }

    public void printLoans() {
        if (loans.isEmpty()) {
            System.out.println("No active loans.");
            return;
        }
        loans.forEach(record -> System.out.printf(
                "Loan #%d - Member %s - ISBN %s - Borrowed %s - Due %s%n",
                record.loanId(), record.memberId(), record.isbn().value(),
                record.borrowDate(), record.dueDate()));
    }

    public void printItemsByType() {
        items.forEach(item -> {
            String description = switch (item) {
                case BookItem b -> "Book:      " + formatItem(b);
                default -> "Unknown item: " + item.getTitle();
            };
            System.out.println(description);
        });
    }

    private String formatItem(LibraryItem item) {
        return switch (item) {
            case BookItem b -> String.format("%s (ISBN %s) by %s [%s] - %s",
                    b.getTitle(), b.getIsbn().value(), b.getAuthor(), b.getGenre(),
                    b.isAvailable() ? "Available" : "On loan");
            default -> item.getTitle();
        };
    }

    // overloading: by ID (for non-book items) vs by ISBN
    public LibraryItem findItemById(String id) throws LibraryException {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new LibraryException("Item not found for ID " + id));
    }

    public LibraryItem findItemByIsbn(Isbn isbn) throws LibraryException {
        return items.stream()
                .filter(item -> item instanceof BookItem b && b.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new LibraryException("Book not found for ISBN " + isbn.value()));
    }

    public LibraryUser findUserById(String memberId) throws LibraryException {
        return users.stream()
                .filter(u -> u.getId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new LibraryException("No user with ID " + memberId));
    }

    public void addBook(BookItem book) {
        Objects.requireNonNull(book);
        boolean exists = items.stream()
                .filter(i -> i instanceof BookItem)
                .map(i -> ((BookItem) i).getIsbn().value())
                .anyMatch(isbn -> isbn.equals(book.getIsbn().value()));
        if (!exists) {
            this.items.add(book);
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Book with ISBN " + book.getIsbn().value() + " already exists.");
        }
    }

    public boolean removeBook(Isbn isbn) throws LibraryException {
        boolean loanActive = loans.stream().anyMatch(l -> l.isbn().equals(isbn));
        if (loanActive) {
            throw new LibraryException("Cannot remove book while it has an active loan.");
        }
        return this.items.removeIf(item -> (item instanceof BookItem b) && b.getIsbn().equals(isbn));
    }

}
