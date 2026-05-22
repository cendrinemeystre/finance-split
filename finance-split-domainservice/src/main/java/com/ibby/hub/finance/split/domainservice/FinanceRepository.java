package com.ibby.hub.finance.split.domainservice;

import com.ibby.hub.finance.split.domain.FinanceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FinanceRepository extends JpaRepository<FinanceData, UUID> {
}
