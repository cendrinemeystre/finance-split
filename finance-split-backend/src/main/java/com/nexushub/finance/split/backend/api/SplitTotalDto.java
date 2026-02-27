package com.nexushub.finance.split.backend.api;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SplitTotalDto {
  @CsvBindByName
  private boolean person;

  @CsvBindByName
  private double amount;
}
