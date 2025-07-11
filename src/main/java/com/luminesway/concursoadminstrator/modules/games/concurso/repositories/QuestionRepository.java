package com.luminesway.concursoadminstrator.modules.games.concurso.repositories;

import com.luminesway.concursoadminstrator.modules.games.concurso.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends CrudRepository<Question, UUID> {
    Page<Question> findAll(Pageable pageable);
}
