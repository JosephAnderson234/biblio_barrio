package com.example.biblio_barrio.reservation.infrastructure;

import com.example.biblio_barrio.reservation.domain.Reservation;
import com.example.biblio_barrio.reservation.domain.ReservationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepository {
}
