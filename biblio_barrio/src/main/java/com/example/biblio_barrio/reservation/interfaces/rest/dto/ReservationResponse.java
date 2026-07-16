package com.example.biblio_barrio.reservation.interfaces.rest.dto;

import java.time.ZonedDateTime;

public record ReservationResponse(
        Long id,
        Long bookId,
        String bookTitle,
        Long userId,
        String userName,
        ZonedDateTime reservedAt,
        ZonedDateTime expiresAt,
        String status
) {
}
