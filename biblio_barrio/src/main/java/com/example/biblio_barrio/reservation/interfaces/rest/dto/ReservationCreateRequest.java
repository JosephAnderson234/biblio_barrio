package com.example.biblio_barrio.reservation.interfaces.rest.dto;

import jakarta.validation.constraints.NotNull;

public record ReservationCreateRequest(
        @NotNull Long bookId
) {
}
