package com.example.biblio_barrio.reservation.domain;

import org.springframework.stereotype.Component;

@Component
public class ReservationEligibilityPolicy {

    public static final int MAX_ACTIVE_RESERVATIONS = 3;

    public void ensureBookUnavailable(boolean bookHasAvailableCopies, Long bookId) {
        if (bookHasAvailableCopies) {
            throw new BookStillAvailableException("Book still has available copies, cannot reserve: " + bookId);
        }
    }

    public void ensureUnderReservationLimit(long activeReservationsForUser) {
        if (activeReservationsForUser >= MAX_ACTIVE_RESERVATIONS) {
            throw new ReservationLimitExceededException(
                    "User already has " + MAX_ACTIVE_RESERVATIONS + " active reservations");
        }
    }
}
