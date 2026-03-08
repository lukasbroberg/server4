package com.example.Server4;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Server4.AuthDtos.AuthResponse;
import com.example.Server4.AuthDtos.LoginRequest;
import com.example.Server4.AuthDtos.RegisterRequest;
import com.example.Server4.AuthDtos.UserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {

        if (userRepository.existsByUsername(req.username())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (userRepository.existsByEmail(req.email())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User u = new User();
        u.setUsername(req.username());
        u.setEmail(req.email());
        u.setPassword(passwordEncoder.encode(req.password()));
        u.setRole(User.Role.USER);
        //u.setRole(User.Role.ADMIN); // tam thoi gan 

        User saved = userRepository.save(u);

        return ResponseEntity.ok(new UserResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getRole().name()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {

        User u = userRepository.findByUsername(req.username()).orElse(null);
        if (u == null) return ResponseEntity.status(401).body("User not found");

        if (!passwordEncoder.matches(req.password(), u.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        String token = jwtService.generateToken(u.getUsername(), u.getRole().name());
        long expiresAt = System.currentTimeMillis() + jwtService.getExpirationMs();

        return ResponseEntity.ok(new AuthResponse(
                token,
                expiresAt,
                new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole().name())
        ));
    }
}