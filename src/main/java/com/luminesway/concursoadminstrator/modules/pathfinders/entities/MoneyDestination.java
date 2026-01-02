package com.luminesway.concursoadminstrator.modules.pathfinders.entities;

import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "money_destination")
public class MoneyDestination {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(unique = true, nullable = false)
    private String code;
    private String status = EnglishConst.ACTIVE;
    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY)
    private List<Cashbook> cashbook;
}
