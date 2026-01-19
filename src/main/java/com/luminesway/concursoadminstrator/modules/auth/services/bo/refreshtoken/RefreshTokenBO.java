package com.luminesway.concursoadminstrator.modules.auth.services.bo.refreshtoken;

import com.luminesway.concursoadminstrator.modules.auth.entities.RefreshTokens;
import com.luminesway.concursoadminstrator.shared.utils.AesCryptoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RefreshTokenBO {
    public Optional<String> obtainRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return Optional.of(cookie.getValue());

                }
            }
        }
        return Optional.empty();
    }


}
