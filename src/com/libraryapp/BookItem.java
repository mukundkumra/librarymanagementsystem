package com.libraryapp;

import java.util.Objects;

public final class BookItem implements LibraryItem, Borrowable {

    private final Isbn isbn;
    private String title;
    private String author;
    private Genre genre;
    private int year;
    private boolean available;

    public BookItem(Isbn isbn, String title, String author, Genre genre, int year) {
        this(isbn, title, author, genre, year, true);
    }

    public BookItem(Isbn isbn, String title, String author, Genre genre,
            int year, boolean available) {
        this.isbn = Objects.requireNonNull(isbn);
        this.title = Objects.requireNonNull(title);
        this.author = Objects.requireNonNull(author);
        this.genre = Objects.requireNonNull(genre);
        this.year = year;
        this.available = available;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    @Override
    public String getId() {
        return isbn.value();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public int getMaxBorrowDays() {
        return 14;
    }

    @Override
    public String toString() {
        return "BookItem{" +
                "isbn=" + isbn.value() +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre=" + genre +
                ", year=" + year +
                ", available=" + available +
                '}';
    }
}
