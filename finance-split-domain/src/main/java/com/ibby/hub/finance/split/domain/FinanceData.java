package com.ibby.hub.finance.split.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "FINANCE_DATA")
public class FinanceData {
  @Id
  @Column(nullable = false)
  private UUID id;

  @Column(name = "DATE_TIME")
  private LocalDateTime dateTime;

  @ManyToOne
  @JoinColumn(name = "PERSON_ID", nullable = false)
  private Person person;

  @Column(name = "AMOUNT")
  private Double amount;

  @Column(name = "DESCRIPTION")
  private String description;

  public FinanceData() {
    this.id = UUID.randomUUID();
  }

  public UUID getId() {return id;}

  public FinanceData(UUID id, LocalDateTime dateTime, Person person, Double amount, String description) {
    this.id = id;
    this.dateTime = dateTime;
    this.person = person;
    this.amount = amount;
    this.description = description;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static FinanceData.Builder builder() {
    return new FinanceData.Builder();
  }

  public static class Builder {
    private UUID id;

    private LocalDateTime dateTime;

    private Person person;

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

    public Builder person(Person person) {
      this.person = person;
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

    public FinanceData build() {
      FinanceData entity = new FinanceData();
      entity.id = this.id;
      entity.dateTime = this.dateTime;
      entity.person = this.person;
      entity.amount = this.amount;
      entity.description = this.description;
      return entity;
    }
  }
}
