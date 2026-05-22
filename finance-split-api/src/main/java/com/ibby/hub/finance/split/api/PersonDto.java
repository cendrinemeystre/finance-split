package com.ibby.hub.finance.split.api;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link Person}
 */
public class PersonDto implements Serializable {
  private UUID id;

  private String name;

  private double total;

  private Set<FinanceDataDto> financeDataSet;

  public PersonDto() {
    // noop
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getTotal() {
    return total;
  }

  public Set<FinanceDataDto> getFinanceDataSet() {
    return financeDataSet;
  }

  public static PersonDto.Builder builder() {
    return new PersonDto.Builder();
  }

  public static class Builder {
    private UUID id;

    private String name;

    private double total;

    private Set<FinanceDataDto> financeDataSet;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder total(double total) {
      this.total = total;
      return this;
    }

    public Builder financeDataSet(Set<FinanceDataDto> financeDataSet) {
      this.financeDataSet = financeDataSet;
      return this;
    }

    public PersonDto build() {
      PersonDto dto = new PersonDto();
      dto.id = this.id;
      dto.name = this.name;
      dto.total = this.total;
      dto.financeDataSet = this.financeDataSet;
      return dto;
    }
  }
}