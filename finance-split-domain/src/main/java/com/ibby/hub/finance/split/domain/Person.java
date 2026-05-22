package com.ibby.hub.finance.split.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "PERSON")
public class Person {
  @Id
  @Column(nullable = false)
  private UUID id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "TOTAL")
  private double total;

  @OneToMany(targetEntity = FinanceData.class, cascade = ALL,
    mappedBy = "person")
  private Set<FinanceData> financeDataSet;

  public Person() {
    this.id = UUID.randomUUID();
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<FinanceData> getFinanceDataSet() {
    return financeDataSet;
  }

  public void setFinanceDataSet(Set<FinanceData> financeDataSet) {
    this.financeDataSet = financeDataSet;
  }

  public static Person.Builder builder() {
    return new Person.Builder();
  }

  public static class Builder {
    private UUID id;

    private String name;

    private double total;

    private Set<FinanceData> financeDataSet;

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

    public Builder financeDataSet(Set<FinanceData> financeDataSet) {
      this.financeDataSet = financeDataSet;
      return this;
    }

    public Person build() {
      Person dto = new Person();
      dto.id = this.id;
      dto.name = this.name;
      dto.total = this.total;
      dto.financeDataSet = this.financeDataSet;
      return dto;
    }
  }
}
