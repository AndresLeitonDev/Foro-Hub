package com.alura.Foro_Hub.services.auth;

public record JWTTokenDto(String token) {

    public JWTTokenDto {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
    }
}
