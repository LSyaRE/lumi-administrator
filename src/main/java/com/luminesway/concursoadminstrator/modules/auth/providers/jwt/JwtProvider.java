package com.luminesway.concursoadminstrator.modules.auth.providers.jwt;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface JwtProvider {
    String generateRefreshToken(UUID userId);
    String generateAccessToken(UUID userId);
    UUID getUserIdFromToken(String token);
    boolean validateToken(String token);
}
