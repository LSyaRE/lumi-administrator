package com.luminesway.concursoadminstrator.modules.auth.services.database.user;

import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService {
    User loadUserById(UUID id);
    User save(User u);
    Optional<User> findByEmail(String email);
    Long count();
    User update(UUID id, User user);
    void delete(UUID id);
    User findById(UUID id);
    Page<User> findAll(Pageable pageable);
}
