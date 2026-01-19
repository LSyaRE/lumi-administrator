package com.luminesway.concursoadminstrator.modules.auth.entities;

import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "refresh_tokens", schema = "auth")
@EntityListeners(AuditingEntityListener.class)
public class RefreshTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @Column(length = 500, nullable = false)
    private String token;
    private String ipUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_rt_user")
    )
    private User user;

    private String status = EnglishConst.ACTIVE;
}
