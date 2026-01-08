package com.luminesway.concursoadminstrator.modules.auth.providers.jwt;

import org.springframework.stereotype.Service;

@Service
public interface JwtProvider {
    public String generateToken(Long userId);
    public Long getUserIdFromToken(String token);
    public boolean validateToken(String token);
}
