package com.luminesway.concursoadminstrator.modules.auth.services.user;

import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User loadUserById(Long id);
    User save(User u);
    Optional<User> findByEmail(String email);
    Long count();
    User update(Long id, User user);
    void delete(Long id);
    User findById(Long id);
    Page<User> findAll(Pageable pageable);
}
