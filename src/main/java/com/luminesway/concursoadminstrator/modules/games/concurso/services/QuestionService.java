package com.luminesway.concursoadminstrator.modules.games.concurso.services;

import com.luminesway.concursoadminstrator.modules.games.concurso.dtos.question.QuestionReqDto;
import com.luminesway.concursoadminstrator.shared.interfaces.CrudService;
import com.luminesway.concursoadminstrator.shared.utils.Result;
import org.hibernate.validator.constraints.UUID;

public interface QuestionService extends CrudService<QuestionReqDto> {

    Result<?> findById(UUID id);

}
