package com.luminesway.concursoadminstrator.shared.interfaces;

import com.luminesway.concursoadminstrator.modules.games.concurso.dtos.question.QuestionReqDto;
import com.luminesway.concursoadminstrator.shared.utils.Result;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CrudService<T> {
    Result<?> create(T payload);
    Result<?> findAll(Pageable pageable);
    Result<?> update(UUID id, T payload);
    Result<?> delete(UUID id);
}
