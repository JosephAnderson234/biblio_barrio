package com.example.biblio_barrio.reservation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    public static final int EXPIRY_HOURS = 48;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private ZonedDateTime reservedAt;

    @Column(nullable = false)
    private ZonedDateTime expiresAt;

    @Column(nullable = false)
    private String status;

    private Reservation(Long bookId, Long userId, ZonedDateTime reservedAt) {
        this.bookId = bookId;
        this.userId = userId;
        this.reservedAt = reservedAt;
        this.expiresAt = reservedAt.plusHours(EXPIRY_HOURS);
        this.status = "PENDING";
    }

    public static Reservation create(Long bookId, Long userId, ZonedDateTime reservedAt) {
        return new Reservation(bookId, userId, reservedAt);
    }
}
