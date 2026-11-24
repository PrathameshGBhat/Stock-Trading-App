package com.trade.user_service.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private static final long ACCESS_TOKEN_EXPIRATION =
            15 * 60 * 1000;

    private static final long REFRESH_TOKEN_EXPIRATION =
            7 * 24 * 60 * 60 * 1000;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate Access Token
    public String generateAccessToken(String userId, String email) {

        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("tokenType", "ACCESS")
                .issuedAt(new Date())
                .expiration(new Date(
                        System.currentTimeMillis()
                                + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    // Generate Refresh Token
    public String generateRefreshToken(String userId) {

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("tokenType", "REFRESH")
                .issuedAt(new Date())
                .expiration(new Date(
                        System.currentTimeMillis()
                                + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    // Validate Token
    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isAccessToken(String token) {
        return "ACCESS".equals(
                extractAllClaims(token).get("tokenType", String.class));
    }

    public boolean isRefreshToken(String token) {
        return "REFRESH".equals(
                extractAllClaims(token).get("tokenType", String.class));
    }
}