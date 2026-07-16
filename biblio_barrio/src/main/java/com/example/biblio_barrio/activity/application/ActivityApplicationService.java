package com.example.biblio_barrio.activity.application;

import com.example.biblio_barrio.activity.interfaces.rest.dto.ActivityItemResponse;
import com.example.biblio_barrio.activity.interfaces.rest.dto.LoanActivityItem;
import com.example.biblio_barrio.activity.interfaces.rest.dto.ReservationActivityItem;
import com.example.biblio_barrio.book.domain.Book;
import com.example.biblio_barrio.book.domain.BookRepository;
import com.example.biblio_barrio.loan.domain.Loan;
import com.example.biblio_barrio.loan.domain.LoanRepository;
import com.example.biblio_barrio.reservation.domain.Reservation;
import com.example.biblio_barrio.reservation.domain.ReservationRepository;
import com.example.biblio_barrio.shared.web.dto.PageResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityApplicationService {

    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;

    public PageResponse<ActivityItemResponse> getMyActivity(Long userId, String type, int page, int size) {
        List<Entry> entries = new ArrayList<>();

        if (!"reservations".equals(type)) {
            for (Loan loan : loanRepository.findByUserId(userId)) {
                entries.add(new Entry(loan.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant(), toItem(loan)));
            }
        }

        if (!"loans".equals(type)) {
            for (Reservation reservation : reservationRepository.findByUserId(userId)) {
                entries.add(new Entry(reservation.getReservedAt().toInstant(), toItem(reservation)));
            }
        }

        entries.sort(Comparator.comparing(Entry::sortKey).reversed());

        long totalElements = entries.size();
        int fromIndex = Math.min(page * size, entries.size());
        int toIndex = Math.min(fromIndex + size, entries.size());

        List<ActivityItemResponse> content = entries.subList(fromIndex, toIndex).stream()
                .map(Entry::item)
                .toList();

        return new PageResponse<>(content, page, size, totalElements);
    }

    private LoanActivityItem toItem(Loan loan) {
        String bookTitle = bookRepository.findById(loan.getBookId()).map(Book::getTitle).orElse(null);
        return new LoanActivityItem(loan.getId(), loan.getBookId(), bookTitle, loan.getBorrowDate(), loan.getDueDate(), loan.getStatus());
    }

    private ReservationActivityItem toItem(Reservation reservation) {
        String bookTitle = bookRepository.findById(reservation.getBookId()).map(Book::getTitle).orElse(null);
        return new ReservationActivityItem(
                reservation.getId(), reservation.getBookId(), bookTitle,
                reservation.getReservedAt(), reservation.getExpiresAt(), reservation.getStatus()
        );
    }

    private record Entry(Instant sortKey, ActivityItemResponse item) {
    }
}
