package com.ibby.hub.finance.split.domainservice.converter;

import com.ibby.hub.finance.split.api.FinanceDataDto;
import com.ibby.hub.finance.split.domain.FinanceData;
import com.ibby.hub.finance.split.domain.Person;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class FinanceDataConverter {

  public FinanceData convertToEntity(FinanceDataDto financeDataDto) {
    FinanceData.Builder builder = FinanceData.builder()
      .id(UUID.randomUUID())
      .dateTime(LocalDateTime.now())
      .person(Person.builder().id(UUID.fromString(financeDataDto.getPersonId())).build())
      .amount(financeDataDto.getAmount())
      .description(financeDataDto.getDescription());
    if (financeDataDto.getId() != null) {
      builder.id(financeDataDto.getId());
    } else {
      builder.id(UUID.randomUUID());
    }
    return builder.build();
  }

  public FinanceDataDto convertToDto(FinanceData financeData) {
    FinanceDataDto.Builder builder = FinanceDataDto.builder()
      .id(financeData.getId())
      .dateTime(financeData.getDateTime())
      .personId(financeData.getPerson().getId().toString())
      .amount(financeData.getAmount())
      .description(financeData.getDescription());
    if (financeData.getId() != null) {
      builder.id(financeData.getId());
    } else {
      builder.id(UUID.randomUUID());
    }
    return builder.build();
  }
}
