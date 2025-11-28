package com.libraryapp;

import java.time.LocalDate;
import java.util.Scanner;

public class LibraryApp {

    private final Scanner scanner = new Scanner(System.in);
    private final Library library;

    public LibraryApp() {
        this.library = Data.createLibrary();
    }

    public static void main(String[] args) {
        var app = new LibraryApp();
        app.run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                running = handleChoice(choice);
            } catch (InvalidCommandException e) {
                System.out.println("Invalid command: " + e.getMessage());
            } catch (LibraryException e) { // checked exceptions
                System.out.println("Error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
        System.out.println("Goodbye!");
    }

    private void printMenu() {
        StringBuilder sb = new StringBuilder(); // StringBuilder from core API
        sb.append("1. List all items\n")
                .append("2. Search items by title\n")
                .append("3. Borrow a book\n")
                .append("4. Return a book\n")
                .append("5. List active loans\n")
                .append("6. Add a book (Admin Only)\n")
                .append("7. Remove a book by ISBN (Admin Only)\n")
                .append("0. Exit\n")
                .append("Choose an option: ");
        System.out.print(sb);
    }

    private boolean handleChoice(String choiceStr) throws LibraryException {
        if (choiceStr.isBlank()) {
            throw new InvalidCommandException("Choice cannot be empty");
        }
        int choice;
        try {
            choice = Integer.parseInt(choiceStr);
        } catch (NumberFormatException ex) {
            throw new InvalidCommandException("Choice must be a number");
        }
        return handleChoice(choice);
    }

    private boolean handleChoice(int choice) throws LibraryException {
        return switch (choice) {
            case 1 -> {
                listAllItems();
                yield true;
            }
            case 2 -> {
                searchItems();
                yield true;
            }
            case 3 -> {
                borrowBook();
                yield true;
            }
            case 4 -> {
                returnBook();
                yield true;
            }
            case 5 -> {
                listLoans();
                yield true;
            }
            case 6-> {
                addBook();
                yield true;
            }
            case 7 -> {
                removeBook();
                yield true;
            }

            case 0 -> false;
            default -> throw new InvalidCommandException("Unknown menu option: " + choice);
        };
    }

    private void listAllItems() {
        System.out.println("\n--- All Library Items ---");
        library.printAllItems();
    }

    private void searchItems() {
        System.out.print("Enter part of the title: ");
        String query = scanner.nextLine().trim();
        library.printMatchingItems(item -> item.getTitle().toLowerCase().contains(query.toLowerCase()));
    }

    private void borrowBook() throws LibraryException {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter ISBN: ");
        String isbnStr = scanner.nextLine().trim();
        Isbn isbn = new Isbn(isbnStr);

        LocalDate today = LocalDate.now();
        library.borrowBook(memberId, isbn, today);
        System.out.println("Book borrowed successfully.");
    }

    private void returnBook() throws LibraryException {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter ISBN: ");
        String isbnStr = scanner.nextLine().trim();
        Isbn isbn = new Isbn(isbnStr);

        library.returnBook(memberId, isbn, LocalDate.now());
        System.out.println("Book returned successfully.");
    }

    private void listLoans() {
        System.out.println("\n--- Active Loans ---");
        library.printLoans();
    }

    private void addBook() throws LibraryException {
        System.out.print("Enter admin ID: ");
        String adminId = scanner.nextLine().trim();
        LibraryUser user = null;
        try {
            user = library.findUserById(adminId);
        } catch (LibraryException e) {
            throw new LibraryException("Admin not found.");
        }
        if (!(user instanceof AdminMember)) {
            throw new LibraryException("Only admin can add books.");
        }

        System.out.print("Enter ISBN: ");
        String isbnStr = scanner.nextLine().trim();
        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter author: ");
        String author = scanner.nextLine().trim();
        System.out.print("Enter genre (e.g. TECHNOLOGY, FICTION): ");
        String genreStr = scanner.nextLine().trim();
        System.out.print("Enter year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        Genre genre = Genre.valueOf(genreStr.toUpperCase());
        BookItem book = new BookItem(new Isbn(isbnStr), title, author, genre, year);
        library.addBook(book);
    }

    private void removeBook() throws LibraryException {
        System.out.print("Enter admin ID: ");
        String adminId = scanner.nextLine().trim();
        LibraryUser user = library.findUserById(adminId);
        if (!(user instanceof AdminMember)) {
            throw new LibraryException("Only admin can remove books.");
        }

        System.out.print("Enter ISBN to remove: ");
        String isbnStr = scanner.nextLine().trim();
        Isbn isbn = new Isbn(isbnStr);

        boolean removed = library.removeBook(isbn);
        if (removed) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found or could not be removed.");
        }
    }

}
