package com.luminesway.concursoadminstrator.modules.auth.services.person;

import com.luminesway.concursoadminstrator.modules.auth.entities.Person;
import org.springframework.stereotype.Service;

@Service
public interface PersonService {
    public Person findOneById(Long id);
    public Person save(Person person);
    public Person delete(Long id);
    public Person update(Long id, Person person);
}
