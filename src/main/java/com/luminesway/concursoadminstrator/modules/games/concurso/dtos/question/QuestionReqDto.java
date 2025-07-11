package com.luminesway.concursoadminstrator.modules.games.concurso.dtos.question;

import com.luminesway.concursoadminstrator.shared.enums.Difficulty;
import com.luminesway.concursoadminstrator.modules.games.concurso.enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class QuestionReqDto {
    @NotNull
    @NotBlank
    private String directory;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    private Difficulty difficulty;
    @NotNull
    @NotBlank
    private String code;
    @NotNull
    private QuestionType type;

    private String description;
}
