package com.luminesway.concursoadminstrator.modules.auth.providers.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;


@Component
public class JwtProviderImpl implements JwtProvider {


    private final SecretKey accessKey;
    private final SecretKey refreshKey;
    @Value("${jwt.access.expiration}")
    private long ACCESS_EXPIRATION = 1000 * 60 * 15;  // 15 minutos
    @Value("${jwt.refresh.expiration}")
    public static long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 días

    public JwtProviderImpl(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.refresh.secret}") String refreshSecret
    ) {
        this.accessKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getSigningKey() {
        return accessKey;
    }

    private SecretKey getRefreshSigningKey() {
        return refreshKey;
    }

    public String generateAccessToken(UUID username) {
        return generateToken(username, ACCESS_EXPIRATION);
    }

    public String generateRefreshToken(UUID username) {
        return generateRefreshToken(username, REFRESH_EXPIRATION);
    }


    private String generateToken(UUID username, long expiration) {
        return Jwts.builder()
                .subject(username.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }


    private String generateRefreshToken(UUID username, long expiration) {
        return Jwts.builder()
                .subject(username.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getRefreshSigningKey())
                .compact();
    }

    public UUID getUserIdFromToken(String token) {
        return UUID.fromString(extractClaim(token, Claims::getSubject));
    }

    @Override
    public UUID getUserIdFromRefreshToken(String token) {
        return UUID.fromString(extractRefreshClaim(token, Claims::getSubject));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getRefreshSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
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

    public <T> T extractRefreshClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getRefreshSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }
}
