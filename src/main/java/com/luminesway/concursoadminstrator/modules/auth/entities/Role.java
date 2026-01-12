package com.luminesway.concursoadminstrator.modules.auth.entities;

import com.luminesway.concursoadminstrator.modules.core.consts.StatusConst;
import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles", schema = "auth")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    String status = EnglishConst.ACTIVE;
}