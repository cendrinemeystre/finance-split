package com.ibby.hub.finance.split.api;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link FinanceData}
 */
public class FinanceDataDto implements Serializable {
  private UUID id;

  private LocalDateTime dateTime;

  private String personId;

  private Double amount;

  private String description;

  public FinanceDataDto() {
  }

  public UUID getId() {
    return id;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public String getPersonId() {
    return personId;
  }

  public Double getAmount() {
    return amount;
  }

  public String getDescription() {
    return description;
  }

  public static FinanceDataDto.Builder builder() {
    return new FinanceDataDto.Builder();
  }

  public static class Builder {
    private UUID id;

    private LocalDateTime dateTime;

    private String personId;

    private Double amount;

    private String description;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder dateTime(LocalDateTime dateTime) {
      this.dateTime = dateTime;
      return this;
    }

    public Builder personId(String personId) {
      this.personId = personId;
      return this;
    }

    public Builder amount(Double amount) {
      this.amount = amount;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public FinanceDataDto build() {
      FinanceDataDto dto = new FinanceDataDto();
      dto.id = this.id;
      dto.dateTime = this.dateTime;
      dto.personId = this.personId;
      dto.amount = this.amount;
      dto.description = this.description;
      return dto;
    }
  }
}