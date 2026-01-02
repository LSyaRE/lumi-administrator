package com.luminesway.concursoadminstrator.modules.pathfinders.repositories;

import com.luminesway.concursoadminstrator.modules.pathfinders.entities.Cashbook;
import com.luminesway.concursoadminstrator.modules.pathfinders.entities.MoneyDestination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MoneyDestinationRepository extends JpaRepository<MoneyDestination, UUID> {
    List<MoneyDestination> findByStatus(String status);
    List<MoneyDestination> findByStatusNot(String status);
    Page<MoneyDestination> findPageByStatus(String status,
                                            Pageable pageable);
}
