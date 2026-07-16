package com.example.biblio_barrio.book.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepository {

    Book save(Book book);

    Optional<Book> findById(Long id);

    boolean existsByIsbn(String isbn);

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByAvailableCopiesGreaterThan(Integer copies, Pageable pageable);

    Page<Book> findByAvailableCopiesEquals(Integer copies, Pageable pageable);
}
