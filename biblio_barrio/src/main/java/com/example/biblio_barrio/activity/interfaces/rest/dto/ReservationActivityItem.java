package com.example.biblio_barrio.activity.interfaces.rest.dto;

import java.time.ZonedDateTime;

public record ReservationActivityItem(
        String type,
        Long id,
        Long bookId,
        String bookTitle,
        ZonedDateTime reservedAt,
        ZonedDateTime expiresAt,
        String status
) implements ActivityItemResponse {

    public ReservationActivityItem(Long id, Long bookId, String bookTitle, ZonedDateTime reservedAt, ZonedDateTime expiresAt, String status) {
        this("RESERVATION", id, bookId, bookTitle, reservedAt, expiresAt, status);
    }
}
