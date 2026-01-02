package com.luminesway.concursoadminstrator.config;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtAuthenticationConverter {

    @Value("${jwt.secret}")
    private final String SECRET_KEY = "unaClaveSuperSecretaYLargaDeAlMenos32Caracteres!!";
    @Value("${jwt.access.expiration}")
    private final long ACCESS_EXPIRATION = 1000 * 60 * 15;  // 15 minutos
    @Value("${jwt.refresh.expiration}")
    private final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 días

    private SecretKey getSigningKey() {
        SecureRandom random = new SecureRandom();
        random.setSeed(SECRET_KEY.getBytes());
        return Jwts.SIG.HS384.key().random(random).build();
    }

    public String generateAccessToken(String username) {
        return generateToken(username, ACCESS_EXPIRATION);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, REFRESH_EXPIRATION);
    }

    private String generateToken(String username, long expiration) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false; // token inválido o expirado
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }
}
