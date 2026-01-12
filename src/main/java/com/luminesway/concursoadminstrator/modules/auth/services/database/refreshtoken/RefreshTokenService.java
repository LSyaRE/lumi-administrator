package com.luminesway.concursoadminstrator.modules.auth.services.database.refreshtoken;

import com.luminesway.concursoadminstrator.modules.auth.entities.RefreshTokens;
import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RefreshTokenService {
    RefreshTokens save(RefreshTokens refreshTokens);

    List<RefreshTokens> findAllByIpUserAndUser(String ipUser, User user);
    List<RefreshTokens> saveAll(List<RefreshTokens> refreshTokens);
}
