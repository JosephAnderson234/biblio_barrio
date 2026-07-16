package com.example.biblio_barrio.book.interfaces.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCreateRequest(
        @NotBlank String title,
        @NotBlank String author,
        String isbn,
        @NotNull @Min(0) Integer totalCopies
) {
}
