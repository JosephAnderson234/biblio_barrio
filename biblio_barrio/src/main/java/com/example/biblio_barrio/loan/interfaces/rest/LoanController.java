package com.example.biblio_barrio.loan.interfaces.rest;

import com.example.biblio_barrio.loan.application.LoanApplicationService;
import com.example.biblio_barrio.loan.interfaces.rest.dto.LoanCreateRequest;
import com.example.biblio_barrio.loan.interfaces.rest.dto.LoanResponse;
import com.example.biblio_barrio.shared.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanApplicationService loanApplicationService;

    @PostMapping
    public ResponseEntity<LoanResponse> create(@Valid @RequestBody LoanCreateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(loanApplicationService.create(request, userId));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanApplicationService.returnLoan(id));
    }
}
