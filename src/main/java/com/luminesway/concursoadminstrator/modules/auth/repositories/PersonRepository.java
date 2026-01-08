package com.luminesway.concursoadminstrator.modules.auth.repositories;


import com.luminesway.concursoadminstrator.modules.auth.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Person findOneById(Long id);
}
