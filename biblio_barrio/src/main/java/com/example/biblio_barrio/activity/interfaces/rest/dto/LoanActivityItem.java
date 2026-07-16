package com.example.biblio_barrio.activity.interfaces.rest.dto;

import java.time.LocalDate;

public record LoanActivityItem(
        String type,
        Long id,
        Long bookId,
        String bookTitle,
        LocalDate borrowDate,
        LocalDate dueDate,
        String status
) implements ActivityItemResponse {

    public LoanActivityItem(Long id, Long bookId, String bookTitle, LocalDate borrowDate, LocalDate dueDate, String status) {
        this("LOAN", id, bookId, bookTitle, borrowDate, dueDate, status);
    }
}
