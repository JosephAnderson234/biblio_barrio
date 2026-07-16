package com.example.biblio_barrio.user.interfaces.rest;

import com.example.biblio_barrio.user.application.AuthApplicationService;
import com.example.biblio_barrio.user.interfaces.rest.dto.LoginRequest;
import com.example.biblio_barrio.user.interfaces.rest.dto.LoginResponse;
import com.example.biblio_barrio.user.interfaces.rest.dto.RegisterRequest;
import com.example.biblio_barrio.user.interfaces.rest.dto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthApplicationService authApplicationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authApplicationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authApplicationService.login(request));
    }
}
