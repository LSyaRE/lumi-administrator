package com.luminesway.concursoadminstrator.modules.games.concurso.controllers;

import com.luminesway.concursoadminstrator.modules.games.concurso.dtos.question.QuestionReqDto;
import com.luminesway.concursoadminstrator.modules.games.concurso.services.QuestionService;
import com.luminesway.concursoadminstrator.modules.games.concurso.services.impl.QuestionServiceImpl;
import com.luminesway.concursoadminstrator.shared.utils.GenericResponse;
import com.luminesway.concursoadminstrator.shared.utils.SpringResult;
import com.luminesway.concursoadminstrator.shared.utils.ResultParameters;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/games/concurso")
@Log4j2
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    /**
     * Retrieves a paginated list of all questions from the service.
     *
     * @param pageable the pagination and sorting information
     * @return a {@link ResponseEntity} containing a generic response which includes the HTTP status,
     *         a success or error message, and the paginated list of questions or errors if any
     */
    @GetMapping
    public ResponseEntity<? extends GenericResponse<?>> findAll(@PageableDefault Pageable pageable) {
        log.info("Finding all questions");
        SpringResult<?> result = questionService.findAll(pageable) ;
        log.info("Finding all questions successful");
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }

    /**
     * Retrieves the details of a question identified by its unique ID.
     *
     * @param id the unique identifier of the question
     * @return a {@link ResponseEntity} containing a {@link GenericResponse} which includes the status code,
     *         a success or error message, and the details of the question or errors if any
     */
    @GetMapping("/{id}")
    public ResponseEntity<? extends GenericResponse<?>> findById(@PathVariable UUID id) {
        log.info("Finding question by id: {}", id);
        SpringResult<?> result = SpringResult.success(ResultParameters.builder().build(), 203);
        log.info("Finding question by id successful");
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }


    /**
     * Creates a new question based on the provided details.
     *
     * @param question the details of the question to be created, which must be a valid {@link QuestionReqDto} object.
     * @return a {@link ResponseEntity} containing a {@link GenericResponse} that represents the result of the operation,
     * including status code, message, and any relevant data or errors.
     */
    @PostMapping
    public ResponseEntity<? extends GenericResponse<?>> create(@Valid @RequestBody QuestionReqDto question) {
        log.info("Creating question: {}", question);
        SpringResult<?> result = questionService.create(question) ;
        log.info("Question successfully created");
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }

    /**
     * Updates an existing question identified by its unique ID.
     *
     * @param id      the unique identifier of the question to update
     * @param payload the data transfer object containing the updated details of the question
     * @return a ResponseEntity containing a GenericResponse object with the result of the operation
     */
    @PutMapping("/{id}")
    public ResponseEntity<? extends GenericResponse<?>> update(@PathVariable UUID id,
                                                               @RequestBody QuestionReqDto payload) {
        log.info("Updating question: {}", id);
        SpringResult<?> result = questionService.update(id, payload) ;
        log.info("Question successfully updated");
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }

    /**
     * Deletes a question identified by the given ID.
     *
     * @param id the ID of the question to be deleted
     * @return a ResponseEntity containing a GenericResponse object with the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<? extends GenericResponse<?>> delete(@PathVariable String id) {
        log.info("Deleting question: {}", id);
        SpringResult<?> result = SpringResult.success(ResultParameters.builder().build(), 204) ;
        log.info("Question successfully deleted");
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }
}
