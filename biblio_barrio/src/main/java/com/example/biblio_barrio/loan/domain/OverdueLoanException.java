package com.example.biblio_barrio.loan.domain;

public class OverdueLoanException extends RuntimeException {
    public OverdueLoanException(String message) {
        super(message);
    }
}
