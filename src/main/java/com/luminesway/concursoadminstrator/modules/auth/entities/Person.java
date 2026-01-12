package com.luminesway.concursoadminstrator.modules.auth.entities;


import com.luminesway.concursoadminstrator.modules.auth.enums.TypeIdentification;
import com.luminesway.concursoadminstrator.modules.core.consts.StatusConst;
import com.luminesway.concursoadminstrator.modules.auth.enums.BloodType;
import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "persons", schema = "auth")
@EntityListeners(AuditingEntityListener.class)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    private Instant createdAt;
    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
    private String name;
    private String lastname;
    private String dni;
    private TypeIdentification typeIdentification;
    private String phonenumber;
    private String address;
    @Enumerated(EnumType.STRING)
    private BloodType bloodtype;
    private String gender;
    private LocalDate birthdate;
    private String email;

    @OneToOne(mappedBy = "person")
    private User user;

    String status = EnglishConst.ACTIVE;
}
