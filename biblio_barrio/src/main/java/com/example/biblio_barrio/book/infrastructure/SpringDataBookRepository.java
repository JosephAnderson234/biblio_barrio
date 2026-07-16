package com.example.biblio_barrio.book.infrastructure;

import com.example.biblio_barrio.book.domain.Book;
import com.example.biblio_barrio.book.domain.BookRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataBookRepository extends JpaRepository<Book, Long>, BookRepository {
}
