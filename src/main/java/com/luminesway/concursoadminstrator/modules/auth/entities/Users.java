package com.luminesway.concursoadminstrator.modules.auth.entities;

import com.luminesway.concursoadminstrator.modules.auth.enums.TypeIdentification;
import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String identification;
    @Column(name = "identification_type")
    private TypeIdentification identificationType;
    private String email;
    private String username;
    private String password;
    private String name;
    private String lastname;
    private List<GrantedAuthority> authorities;
    private String status = EnglishConst.ACTIVE;
    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
