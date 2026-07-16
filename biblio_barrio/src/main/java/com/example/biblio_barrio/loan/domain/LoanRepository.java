package com.example.biblio_barrio.loan.domain;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {

    Loan save(Loan loan);

    Optional<Loan> findById(Long id);

    boolean existsByUserIdAndStatus(Long userId, String status);

    List<Loan> findByUserId(Long userId);
}
