package com.luminesway.concursoadminstrator.modules.auth.services.database.user;


import com.luminesway.concursoadminstrator.modules.core.consts.StatusConst;
import com.luminesway.concursoadminstrator.modules.core.exceptions.NotFoundException;
import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import com.luminesway.concursoadminstrator.modules.auth.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repo) {
        this.userRepository = repo;
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Long count() {
        return this.userRepository.countUserByStatus(StatusConst.ACTIVE);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(UUID id, User user) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("No se puede actualizar, el registro no existe");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proyecto no encontrado"));
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User loadUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }
}
