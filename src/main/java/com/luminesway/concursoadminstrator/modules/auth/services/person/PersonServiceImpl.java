package com.luminesway.concursoadminstrator.modules.auth.services.person;


import com.luminesway.concursoadminstrator.modules.auth.entities.Person;
import com.luminesway.concursoadminstrator.modules.auth.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person findOneById(Long id) {
        return personRepository.findOneById(id);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person delete(Long id) {
        return null;
    }

    @Override
    public Person update(Long id, Person person) {
        return null;
    }
}
