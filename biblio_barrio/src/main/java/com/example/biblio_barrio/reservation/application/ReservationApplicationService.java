package com.example.biblio_barrio.reservation.application;

import com.example.biblio_barrio.book.domain.Book;
import com.example.biblio_barrio.book.domain.BookNotFoundException;
import com.example.biblio_barrio.book.domain.BookRepository;
import com.example.biblio_barrio.reservation.domain.Reservation;
import com.example.biblio_barrio.reservation.domain.ReservationEligibilityPolicy;
import com.example.biblio_barrio.reservation.domain.ReservationRepository;
import com.example.biblio_barrio.reservation.interfaces.rest.dto.ReservationCreateRequest;
import com.example.biblio_barrio.reservation.interfaces.rest.dto.ReservationResponse;
import com.example.biblio_barrio.user.domain.User;
import com.example.biblio_barrio.user.exceptions.UserNotFoundException;
import com.example.biblio_barrio.user.domain.UserRepository;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationApplicationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReservationEligibilityPolicy eligibilityPolicy;

    @Transactional
    public ReservationResponse create(ReservationCreateRequest request, Long userId) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found: " + request.bookId()));

        eligibilityPolicy.ensureBookUnavailable(book.isAvailable(), book.getId());
        eligibilityPolicy.ensureUnderReservationLimit(reservationRepository.countByUserIdAndStatus(userId, "PENDING"));

        Reservation reservation = Reservation.create(book.getId(), userId, ZonedDateTime.now());
        Reservation saved = reservationRepository.save(reservation);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        return new ReservationResponse(
                saved.getId(), saved.getBookId(), book.getTitle(),
                saved.getUserId(), user.getUsername(),
                saved.getReservedAt(), saved.getExpiresAt(), saved.getStatus()
        );
    }
}
