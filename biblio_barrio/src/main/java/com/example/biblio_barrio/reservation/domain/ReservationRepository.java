package com.example.biblio_barrio.reservation.domain;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    long countByUserIdAndStatus(Long userId, String status);

    List<Reservation> findByUserId(Long userId);
}
