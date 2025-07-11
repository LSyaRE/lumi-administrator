package com.luminesway.concursoadminstrator.modules.games.concurso.entities;


import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import com.luminesway.concursoadminstrator.shared.enums.Difficulty;
import com.luminesway.concursoadminstrator.modules.games.concurso.enums.QuestionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Question {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;
        private String directory;
        private String title;
        private String code;
        private Difficulty difficulty;
        private QuestionType type;
        private String description;
        private String status = EnglishConst.ACTIVE;
}
