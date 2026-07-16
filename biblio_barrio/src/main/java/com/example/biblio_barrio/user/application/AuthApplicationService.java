package com.example.biblio_barrio.user.application;

import com.example.biblio_barrio.shared.security.JwtService;
import com.example.biblio_barrio.user.domain.User;
import com.example.biblio_barrio.user.exceptions.UserAlreadyExistsException;
import com.example.biblio_barrio.user.exceptions.UserNotFoundException;
import com.example.biblio_barrio.user.domain.UserRepository;
import com.example.biblio_barrio.user.interfaces.rest.dto.LoginRequest;
import com.example.biblio_barrio.user.interfaces.rest.dto.LoginResponse;
import com.example.biblio_barrio.user.interfaces.rest.dto.RegisterRequest;
import com.example.biblio_barrio.user.interfaces.rest.dto.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthApplicationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException("Username already registered: " + request.username());
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email already registered: " + request.email());
        }

        User user = User.register(request.username(), request.email(), passwordEncoder.encode(request.password()));
        User saved = userRepository.save(user);

        return new RegisterResponse(saved.getId(), saved.getUsername(), saved.getEmail());
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + request.username()));

        String token = jwtService.generateToken(user.getUsername(), user.getId(), user.getRole());

        return new LoginResponse(token, jwtService.getExpirationSeconds());
    }
}
