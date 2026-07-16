package com.example.biblio_barrio.loan.interfaces.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record LoanCreateRequest(
        @NotNull Long bookId,
        LocalDate borrowDate
) {
}
