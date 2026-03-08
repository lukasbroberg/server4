package com.example.Server4;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDtos {

    public record RegisterRequest(
            @NotBlank
            @Size(min = 3, max = 50)
            String username,

            @NotBlank
            @Email
            String email,

            @NotBlank
            @Size(min = 6, max = 100)
            String password
    ) {
    }

    public record LoginRequest(
            @NotBlank
            String username,

            @NotBlank
            String password
    ) {
    }

    public record UserResponse(
            Long id,
            String username,
            String email,
            String role
    ) {
    }

    public record AuthResponse(
            String token,
            long expiresAt,
            UserResponse user
    ) {
    }
}