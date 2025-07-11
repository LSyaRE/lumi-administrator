package com.luminesway.concursoadminstrator.modules.games.concurso.controllers;

import com.luminesway.concursoadminstrator.modules.games.concurso.dtos.question.QuestionReqDto;
import com.luminesway.concursoadminstrator.modules.games.concurso.services.QuestionService;
import com.luminesway.concursoadminstrator.modules.games.concurso.services.impl.QuestionServiceImpl;
import com.luminesway.concursoadminstrator.shared.utils.GenericResponse;
import com.luminesway.concursoadminstrator.shared.utils.Result;
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

    @GetMapping
    public ResponseEntity<? extends GenericResponse<?>> findAll(@PageableDefault Pageable pageable) {
        log.info("Finding all questions");
        Result<?> result = questionService.findAll(pageable) ;
        log.info("Finding all questions successful");
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }

    @GetMapping("/{id}")
    public ResponseEntity<? extends GenericResponse<?>> findById(@PathVariable UUID id) {
        log.info("Finding question by id: {}", id);
        Result<?> result = Result.success(ResultParameters.builder().build(), 203);
        log.info("Finding question by id successful");
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }


    @PostMapping
    public ResponseEntity<? extends GenericResponse<?>> create(@Valid @RequestBody QuestionReqDto question) {
        log.info("Creating question: {}", question);
        Result<?> result = questionService.create(question) ;
        log.info("Question successfully created");
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }

    @PutMapping("/{id}")
    public ResponseEntity<? extends GenericResponse<?>> update(@PathVariable UUID id,
                                                               @RequestBody QuestionReqDto payload) {
        log.info("Updating question: {}", id);
        Result<?> result = questionService.update(id, payload) ;
        log.info("Question successfully updated");
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<? extends GenericResponse<?>> delete(@PathVariable String id) {
        log.info("Deleting question: {}", id);
        Result<?> result = Result.success(ResultParameters.builder().build(), 204) ;
        log.info("Question successfully deleted");
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }
}
