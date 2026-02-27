package com.nexushub.finance.split.backend.api;

import com.nexushub.finance.split.backend.converter.LocalDateTimeConverter;
import com.nexushub.finance.split.backend.converter.UUIDConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SplitDto {
  @CsvCustomBindByName(converter = UUIDConverter.class)
  private UUID id;

  @CsvCustomBindByName(converter = LocalDateTimeConverter.class)
  private LocalDateTime localDateTime;

  @CsvBindByName
  private boolean person;

  @CsvBindByName
  private double amount;

  @CsvBindByName
  private String description;
}