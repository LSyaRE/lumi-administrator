package com.luminesway.concursoadminstrator.modules.auth.services.database.refreshtoken;

import com.luminesway.concursoadminstrator.modules.auth.entities.RefreshTokens;
import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import com.luminesway.concursoadminstrator.modules.auth.repositories.RefreshTokenRepository;
import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.repository = refreshTokenRepository;
    }


    @Override
    public RefreshTokens save(RefreshTokens refreshTokens) {
        return repository.save(refreshTokens);
    }

    @Override
    public List<RefreshTokens> findAllByUser(User user) {
        return repository.findAllByStatusAndUser(EnglishConst.ACTIVE, user);
    }

    @Override
    public List<RefreshTokens> findAllByIpUserAndUser(String ipUser, User user) {
        return repository.findAllByStatusAndIpUserAndUser(EnglishConst.ACTIVE, ipUser, user);
    }

    public List<RefreshTokens> saveAll(List<RefreshTokens> refreshTokens) {
        return repository.saveAll(refreshTokens);
    }
}
