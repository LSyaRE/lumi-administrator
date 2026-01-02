package com.luminesway.concursoadminstrator.modules.pathfinders.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luminesway.concursoadminstrator.modules.pathfinders.enums.FinancesType;
import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class  Cashbook {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    private String observation;
    private Long amount;
    private Long taxes;
    private FinancesType type;
    private String status = EnglishConst.ACTIVE;
    @ManyToOne
    private MoneyDestination destination;
    @ManyToOne
    private AccountBalance accountBalance;
}
