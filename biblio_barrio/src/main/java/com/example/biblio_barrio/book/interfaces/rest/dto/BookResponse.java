package com.example.biblio_barrio.book.interfaces.rest.dto;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        Integer totalCopies,
        Integer availableCopies
) {
}
