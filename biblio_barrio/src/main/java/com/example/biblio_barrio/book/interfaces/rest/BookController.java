package com.example.biblio_barrio.book.interfaces.rest;

import com.example.biblio_barrio.book.application.BookApplicationService;
import com.example.biblio_barrio.book.interfaces.rest.dto.BookCreateRequest;
import com.example.biblio_barrio.book.interfaces.rest.dto.BookResponse;
import com.example.biblio_barrio.book.interfaces.rest.dto.BookSummaryResponse;
import com.example.biblio_barrio.shared.web.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookApplicationService bookApplicationService;

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookApplicationService.create(request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookSummaryResponse>> list(
            @RequestParam(defaultValue = "all") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(bookApplicationService.list(status, page, size));
    }
}
