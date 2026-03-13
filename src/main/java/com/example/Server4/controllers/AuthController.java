package com.example.Server4.controllers;

import com.example.Server4.dto.AuthResponse;
import com.example.Server4.dto.LoginRequest;
import com.example.Server4.dto.RegisterRequest;
import com.example.Server4.dto.UserResponse;
import com.example.Server4.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        UserResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /*@PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);*/
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("=== BAT DAU LOGIN ===");
            System.out.println("username = " + request.getUsername());

            AuthResponse response = authService.login(request);

            System.out.println("=== LOGIN OK ===");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("=== LOGIN ERROR ===");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Login loi: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        UserResponse response = authService.getCurrentUser(authentication);
        return ResponseEntity.ok(response);
    }
}