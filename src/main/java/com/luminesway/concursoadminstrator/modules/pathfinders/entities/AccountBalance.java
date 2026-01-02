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
public class AccountBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Long amount;
    private String status = EnglishConst.ACTIVE;
    @OneToMany(mappedBy = "accountBalance", fetch = FetchType.LAZY)
    private List<Cashbook> cashbook;
}
