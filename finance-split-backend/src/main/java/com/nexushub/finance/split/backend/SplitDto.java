package com.nexushub.finance.split.backend;

import com.opencsv.bean.CsvBindByName;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SplitDto {
  @NonNull
  @CsvBindByName
  private UUID id;

  @NonNull
  @CsvBindByName
  private LocalDateTime localDateTime;

  @CsvBindByName
  private boolean cendrine;

  @CsvBindByName
  private double amount;

  @CsvBindByName
  private String description;
}