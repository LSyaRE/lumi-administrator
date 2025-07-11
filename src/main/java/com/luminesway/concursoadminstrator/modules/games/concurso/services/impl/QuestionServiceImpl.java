package com.luminesway.concursoadminstrator.modules.games.concurso.services.impl;

import com.luminesway.concursoadminstrator.modules.games.concurso.dtos.question.QuestionReqDto;
import com.luminesway.concursoadminstrator.modules.games.concurso.entities.Question;
import com.luminesway.concursoadminstrator.modules.games.concurso.repositories.QuestionRepository;
import com.luminesway.concursoadminstrator.modules.games.concurso.services.QuestionService;
import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import com.luminesway.concursoadminstrator.shared.utils.Mapping;
import com.luminesway.concursoadminstrator.shared.utils.Result;
import com.luminesway.concursoadminstrator.shared.utils.ResultParameters;
import com.luminesway.concursoadminstrator.shared.utils.WeakMapping;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.luminesway.concursoadminstrator.shared.consts.GenericMessages.CONVERTING_QUESTION;
import static com.luminesway.concursoadminstrator.shared.consts.GenericMessages.SENDING_RES;

@Service
@Log4j2
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final Mapping mapping;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.mapping = new WeakMapping();
    }

    public Result<?> create(QuestionReqDto payload) {
        try {
            log.info(CONVERTING_QUESTION);
            Question question = mapping.execute(payload, Question.class);

            log.info("Creating Question");
            Question savedQuestion = questionRepository.save(question);
            log.info("Question created");
            log.info(SENDING_RES);
            return Result.success(ResultParameters.<Question>builder().result(savedQuestion).message("Se ha creado exitosamente la pregunta").build(), 201);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(ResultParameters.builder().message("Algo ha ocurrido por favor informar a sistemas.").build(), 500);
        }
    }

    public Result<?> findAll(Pageable pageable) {
        Page<Question> questions = questionRepository.findAll(pageable);
        return Result.success(ResultParameters.builder()
                .message("Se han encontrado todas las preguntas")
                .result(questions)
                .build(), 200);
    }


    public Result<?> update(UUID id, QuestionReqDto payload) {
        log.info("Searching Question");
        Question question = questionRepository.findById(id).orElse(null);

        log.info("Validating if Question exists");
        if (question == null) {
            return Result.error(ResultParameters.builder()
                    .message("No se ha encontrado la pregunta")
                    .build(), 404);
        }

        try {
            log.info(CONVERTING_QUESTION);
            Question replaceInfo = mapping.execute(payload, question.getClass());
            log.info("Updating Question");
            questionRepository.save(replaceInfo);
        } catch (Exception e) {
            log.error("Error converting QuestionReqDto to Question or Saving Question");
            log.error(e.getMessage());
            return Result.error(ResultParameters.builder()
                    .message("Sucedio algo al transformar la informacion.")
                    .build(), 500);
        }
        log.info(SENDING_RES);
        return Result.success(ResultParameters.builder().
                message("Se ha actualizado correctamente")
                .build(),200);
    }

    public Result<?> delete(UUID id) {
        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            return Result.error(ResultParameters.builder()
                    .message("No se ha encontrado la pregunta")
                    .build(), 404);
        }

        question.setStatus(EnglishConst.DELETED);
        log.info("Deleting Question");
        questionRepository.save(question);

        return Result.success(ResultParameters.builder().message("Se ha eliminado con exito").build(), 204);
    }

    @Override
    public Result<?> findById(org.hibernate.validator.constraints.UUID id) {
        return null;
    }
}
