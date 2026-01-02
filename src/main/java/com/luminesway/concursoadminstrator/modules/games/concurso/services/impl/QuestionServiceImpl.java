package com.luminesway.concursoadminstrator.modules.games.concurso.services.impl;

import com.luminesway.concursoadminstrator.modules.games.concurso.dtos.question.QuestionReqDto;
import com.luminesway.concursoadminstrator.modules.games.concurso.entities.Question;
import com.luminesway.concursoadminstrator.modules.games.concurso.repositories.QuestionRepository;
import com.luminesway.concursoadminstrator.modules.games.concurso.services.QuestionService;
import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
import com.luminesway.concursoadminstrator.shared.utils.Mapping;
import com.luminesway.concursoadminstrator.shared.utils.SpringResult;
import com.luminesway.concursoadminstrator.shared.utils.ResultParameters;
import com.luminesway.concursoadminstrator.shared.utils.WeakMapping;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public SpringResult<?> create(QuestionReqDto payload) {
        try {
            log.info(CONVERTING_QUESTION);
            Question question = mapping.execute(payload, Question.class);

            log.info("Creating Question");
            Question savedQuestion = questionRepository.save(question);
            log.info("Question created");
            log.info(SENDING_RES);
            return SpringResult.success(ResultParameters.<Question>builder().result(savedQuestion).message("Question created successfully").build(), 201);
        } catch (Exception e) {
            log.error(e);
            return SpringResult.error(ResultParameters.builder().message("Unexpected error while creating Question.").build(), 500);
        }
    }

    public SpringResult<?> findAll(Pageable pageable) {
        Page<Question> questions = questionRepository.findAll(pageable);
        return SpringResult.success(ResultParameters.builder()
                .message("Se han encontrado todas las preguntas")
                .result(questions)
                .build(), 200);
    }


    public SpringResult<?> update(UUID id, QuestionReqDto payload) {
        log.info("Searching Question");
        Question question = questionRepository.findById(id).orElse(null);

        log.info("Validating if Question exists");
        if (question == null) {
            return SpringResult.error(ResultParameters.builder()
                    .message("Question not found")
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
            return SpringResult.error(ResultParameters.builder()
                    .message("Error converting the question or saving it.")
                    .build(), 500);
        }
        log.info(SENDING_RES);
        return SpringResult.success(ResultParameters.builder().
                message("Updated successfully")
                .build(),200);
    }

    public SpringResult<?> delete(UUID id) {
        Question question = questionRepository.findById(id).orElse(null);

        if (question == null) {
            return SpringResult.error(ResultParameters.builder()
                    .message("Question not found")
                    .build(), 404);
        }

        question.setStatus(EnglishConst.DELETED);
        log.info("Deleting Question");
        questionRepository.save(question);

        return SpringResult.success(ResultParameters.builder().message("Deleted successfully").build(), 204);
    }

    @Override
    public SpringResult<?> findById(org.hibernate.validator.constraints.UUID id) {
        return null;
    }
}
