package com.example.biblio_barrio.loan.interfaces.rest.dto;

import java.time.LocalDate;

public record LoanResponse(
        Long id,
        Long bookId,
        Long userId,
        String borrowerName,
        LocalDate borrowDate,
        LocalDate dueDate,
        String status
) {
}
