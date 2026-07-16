package com.example.biblio_barrio.book.interfaces.rest.dto;

public record BookSummaryResponse(
        Long id,
        String title,
        String author,
        Integer availableCopies,
        Integer totalCopies
) {
}
