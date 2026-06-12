package com.ibby.hub.finance.split.domainservice;

import com.ibby.hub.finance.split.domain.FinanceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FinanceRepository extends JpaRepository<FinanceData, UUID> {
  @Query("SELECT f FROM FinanceData f ORDER BY f.dateTime ASC")
  List<FinanceData> findAllOrdered();
}
