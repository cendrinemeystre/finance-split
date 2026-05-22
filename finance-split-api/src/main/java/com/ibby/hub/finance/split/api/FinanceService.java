package com.ibby.hub.finance.split.api;

public interface FinanceService {
  FinanceDataDto[] findAll();

  FinanceDataDto createFinanceData(FinanceDataDto financeDataDto);

  void deleteFinanceData(String id);
}
