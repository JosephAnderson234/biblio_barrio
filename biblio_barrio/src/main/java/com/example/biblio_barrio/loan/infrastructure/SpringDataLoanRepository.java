package com.example.biblio_barrio.loan.infrastructure;

import com.example.biblio_barrio.loan.domain.Loan;
import com.example.biblio_barrio.loan.domain.LoanRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataLoanRepository extends JpaRepository<Loan, Long>, LoanRepository {
}
