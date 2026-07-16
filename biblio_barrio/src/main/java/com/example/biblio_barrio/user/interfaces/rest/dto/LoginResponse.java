package com.example.biblio_barrio.user.interfaces.rest.dto;

public record LoginResponse(
        String token,
        long expiresIn
) {
}
