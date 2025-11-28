package com.libraryapp;

import java.util.ArrayList;

public final class Data {

    private Data() {
    }

    public static Library createLibrary() {
        var items = new ArrayList<LibraryItem>();

        items.add(new BookItem(new Isbn("9781569319307"), "Dragon Ball", "Akira Toriyama", Genre.FICTION, 1985));
        items.add(new BookItem(new Isbn("9780134685991"), "Effective Java", "Joshua Bloch", Genre.TECHNOLOGY, 2018));
        items.add(new BookItem(new Isbn("9780596009205"), "Head First Design Patterns", "Eric Freeman", Genre.TECHNOLOGY, 2004));
        items.add(new BookItem(new Isbn("9780262033848"), "Introduction to Algorithms", "Cormen et al.", Genre.TECHNOLOGY, 2009));
        items.add(new BookItem(new Isbn("9780143127741"), "Thinking, Fast and Slow", "Daniel Kahneman", Genre.PSYCHOLOGY, 2011));
        items.add(new BookItem(new Isbn("9780307277785"), "The Lean Startup", "Eric Ries", Genre.BUSINESS, 2011));
        items.add(new BookItem(new Isbn("9780553380163"), "The Selfish Gene", "Richard Dawkins", Genre.SCIENCE, 2006));
        items.add(new BookItem(new Isbn("9780451524935"), "1984", "George Orwell", Genre.FICTION, 1949));

        var loans = new ArrayList<LoanRecord>();
        var users = new ArrayList<LibraryUser>();

        users.add(new AdminMember("A001", "Admin Super"));
        users.add(new StudentMember("S001", "Jane Student", "CS"));
        users.add(new StaffMember("T001", "Joe Staff", "Library"));

        return new Library(items, loans, users);
    }
}
