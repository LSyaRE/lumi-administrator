package com.luminesway.concursoadminstrator.modules.auth.repositories;

import com.luminesway.concursoadminstrator.modules.auth.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Page<Role> findAll(Pageable pageable);
    Optional<Role> findByName(String name);
}