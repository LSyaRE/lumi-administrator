package com.luminesway.concursoadminstrator.modules.auth.repositories;

import com.luminesway.concursoadminstrator.modules.auth.entities.RefreshTokens;
import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RefreshTokenRepository  extends JpaRepository<RefreshTokens, UUID> {
    Page<RefreshTokens> findAllByStatus(Pageable pageable,String status);
    List<RefreshTokens> findAllByStatusAndIpUserAndUser(String status, String ipUser, User user);
    List<RefreshTokens> findAllByStatusAndUser(String status, User user);
}
