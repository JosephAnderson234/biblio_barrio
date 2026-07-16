package com.example.biblio_barrio.loan.domain;

import org.springframework.stereotype.Component;

@Component
public class LoanEligibilityPolicy {

    public void ensureNoOverdueLoans(boolean userHasOverdueLoans) {
        if (userHasOverdueLoans) {
            throw new OverdueLoanException("User has overdue loans and cannot borrow new books");
        }
    }
}
