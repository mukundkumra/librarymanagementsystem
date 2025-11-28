package com.libraryapp;

import java.time.LocalDate;

public record LoanRecord(
        int loanId,
        Isbn isbn,
        String memberId,
        LocalDate borrowDate,
        LocalDate dueDate) {
}
