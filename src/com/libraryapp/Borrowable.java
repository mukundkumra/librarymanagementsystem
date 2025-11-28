package com.libraryapp;

import java.time.LocalDate;

public interface Borrowable {

    int getMaxBorrowDays();

    default LocalDate calculateDueDate(LocalDate from) {
        validateBorrowDate(from); // private interface method
        return from.plusDays(getMaxBorrowDays());
    }

    private void validateBorrowDate(LocalDate from) {
        if (from == null) {
            throw new IllegalArgumentException("Borrow date cannot be null");
        }
    }

    static boolean isOverdue(LocalDate dueDate, LocalDate on) {
        return on.isAfter(dueDate);
    }
}
