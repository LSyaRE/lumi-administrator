package com.luminesway.concursoadminstrator.modules.auth.repositories;

import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);

    Long countUserByStatus(String status);
}