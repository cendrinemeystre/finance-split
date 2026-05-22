package com.ibby.hub.finance.split.domainservice;

import com.ibby.hub.finance.split.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
  @Transactional
  @Modifying
  @Query("update Person p set p.total = p.total + ?1 where p.id = ?2")
  void updateAddTotalFor(double total, UUID id);

  @Transactional
  @Modifying
  @Query("update Person p set p.total = p.total - ?1 where p.id = ?2")
  void updateSubtractTotalFor(double total, UUID id);
}
