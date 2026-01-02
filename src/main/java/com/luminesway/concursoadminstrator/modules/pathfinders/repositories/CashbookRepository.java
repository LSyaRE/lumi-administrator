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
public interface CashbookRepository extends JpaRepository<Cashbook, UUID> {
    List<Cashbook> findByStatus(String status);
    List<Cashbook> findByStatusNot(String status);
    Page<Cashbook> findPageByStatus(String status,
                                            Pageable pageable);
}
