package com.example.Server4;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;
    private final String issuer;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms:3600000}") long expirationMs,
            @Value("${app.jwt.issuer:server4}") String issuer
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
        this.issuer = issuer;
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public String generateToken(String username, String role) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .issuer(issuer)
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationMs))
                .claims(Map.of("role", role))
                .signWith(key)
                .compact();
    }

    private Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return parse(token).getSubject();
    }

    public String extractRole(String token) {
        Object role = parse(token).get("role");
        return role == null ? null : role.toString();
    }

}
