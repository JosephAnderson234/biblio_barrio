package com.example.biblio_barrio.reservation.domain;

public class BookStillAvailableException extends RuntimeException {
    public BookStillAvailableException(String message) {
        super(message);
    }
}
