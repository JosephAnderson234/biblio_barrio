package com.example.biblio_barrio.book.application;

import com.example.biblio_barrio.book.domain.Book;
import com.example.biblio_barrio.book.domain.BookRepository;
import com.example.biblio_barrio.book.interfaces.rest.dto.BookCreateRequest;
import com.example.biblio_barrio.book.interfaces.rest.dto.BookResponse;
import com.example.biblio_barrio.book.interfaces.rest.dto.BookSummaryResponse;
import com.example.biblio_barrio.shared.web.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookApplicationService {

    private final BookRepository bookRepository;

    public BookResponse create(BookCreateRequest request) {
        if (request.isbn() != null && !request.isbn().isBlank() && bookRepository.existsByIsbn(request.isbn())) {
            throw new DataIntegrityViolationException("ISBN already registered: " + request.isbn());//ISBN already registered: 45546551515
        }

        Book book = Book.create(request.title(), request.author(), request.isbn(), request.totalCopies());
        Book saved = bookRepository.save(book);

        return toResponse(saved);
    }

    public PageResponse<BookSummaryResponse> list(String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> result = switch (status == null ? "all" : status) {
            case "available" -> bookRepository.findByAvailableCopiesGreaterThan(0, pageable);
            case "unavailable" -> bookRepository.findByAvailableCopiesEquals(0, pageable);
            default -> bookRepository.findAll(pageable);
        };

        var content = result.getContent().stream().map(this::toSummary).toList();

        return new PageResponse<>(content, result.getNumber(), result.getSize(), result.getTotalElements());
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(), book.getTitle(), book.getAuthor(),
                book.getIsbn(), book.getTotalCopies(), book.getAvailableCopies()
        );
    }

    private BookSummaryResponse toSummary(Book book) {
        return new BookSummaryResponse(
                book.getId(), book.getTitle(), book.getAuthor(),
                book.getAvailableCopies(), book.getTotalCopies()
        );
    }
}
