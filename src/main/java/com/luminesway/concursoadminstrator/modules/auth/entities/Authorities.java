package com.luminesway.concursoadminstrator.modules.auth.entities;

import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Authorities implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String authority;
    private String description;

    private String status = EnglishConst.ACTIVE;
    @Override
    public String getAuthority() {
        return authority;
    }
}
