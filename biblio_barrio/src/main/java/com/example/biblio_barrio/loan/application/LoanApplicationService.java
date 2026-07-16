package com.example.biblio_barrio.loan.application;

import com.example.biblio_barrio.book.domain.Book;
import com.example.biblio_barrio.book.domain.BookNotFoundException;
import com.example.biblio_barrio.book.domain.BookRepository;
import com.example.biblio_barrio.loan.domain.Loan;
import com.example.biblio_barrio.loan.domain.LoanEligibilityPolicy;
import com.example.biblio_barrio.loan.domain.LoanNotFoundException;
import com.example.biblio_barrio.loan.domain.LoanRepository;
import com.example.biblio_barrio.loan.interfaces.rest.dto.LoanCreateRequest;
import com.example.biblio_barrio.loan.interfaces.rest.dto.LoanResponse;
import com.example.biblio_barrio.user.domain.User;
import com.example.biblio_barrio.user.exceptions.UserNotFoundException;
import com.example.biblio_barrio.user.domain.UserRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanEligibilityPolicy loanEligibilityPolicy;

    @Transactional
    public LoanResponse create(LoanCreateRequest request, Long userId) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found: " + request.bookId()));

        boolean hasOverdueLoans = loanRepository.existsByUserIdAndStatus(userId, "OVERDUE");
        loanEligibilityPolicy.ensureNoOverdueLoans(hasOverdueLoans);

        book.borrowCopy();
        bookRepository.save(book);

        LocalDate borrowDate = request.borrowDate() != null ? request.borrowDate() : LocalDate.now();
        Loan loan = Loan.borrow(book.getId(), userId, borrowDate);
        Loan saved = loanRepository.save(loan);

        return toResponse(saved);
    }

    @Transactional
    public LoanResponse returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found: " + loanId));

        loan.markReturned();
        Loan saved = loanRepository.save(loan);

        Book book = bookRepository.findById(loan.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found: " + loan.getBookId()));
        book.returnCopy();
        bookRepository.save(book);

        return toResponse(saved);
    }

    private LoanResponse toResponse(Loan loan) {
        User user = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + loan.getUserId()));

        return new LoanResponse(
                loan.getId(), loan.getBookId(), loan.getUserId(), user.getUsername(),
                loan.getBorrowDate(), loan.getDueDate(), loan.getStatus()
        );
    }
}
