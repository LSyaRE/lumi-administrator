package com.luminesway.concursoadminstrator.modules.games.concurso.services;

import com.luminesway.concursoadminstrator.modules.games.concurso.dtos.question.QuestionReqDto;
import com.luminesway.concursoadminstrator.shared.interfaces.CrudService;
import com.luminesway.concursoadminstrator.shared.utils.SpringResult;
import org.hibernate.validator.constraints.UUID;

public interface QuestionService extends CrudService<QuestionReqDto> {

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id the unique identifier of the entity to retrieve
     * @return a {@code Result} object containing the retrieved entity or error details
     */
    SpringResult<?> findById(UUID id);

}
