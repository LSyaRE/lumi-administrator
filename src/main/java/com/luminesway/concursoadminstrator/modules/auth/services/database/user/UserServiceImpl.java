package com.luminesway.concursoadminstrator.modules.auth.services.database.user;


import com.luminesway.concursoadminstrator.modules.core.exceptions.NotFoundException;
import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import com.luminesway.concursoadminstrator.modules.auth.repositories.UserRepository;
import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
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
        return this.userRepository.countUserByStatus(EnglishConst.ACTIVE);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return "";
            }

        };


        return userDetails;
    }
}
