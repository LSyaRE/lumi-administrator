package com.luminesway.concursoadminstrator.modules.auth.repositories;


import com.luminesway.concursoadminstrator.modules.auth.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Person findOneById(UUID id);
}
