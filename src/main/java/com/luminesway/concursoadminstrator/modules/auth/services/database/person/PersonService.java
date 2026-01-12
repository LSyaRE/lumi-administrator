package com.luminesway.concursoadminstrator.modules.auth.services.database.person;

import com.luminesway.concursoadminstrator.modules.auth.entities.Person;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PersonService {
    Person findOneById(UUID id);
    Person save(Person person);
    Person delete(UUID id);
    Person update(UUID id, Person person);
}
