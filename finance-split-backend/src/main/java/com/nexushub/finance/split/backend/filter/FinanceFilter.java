package com.nexushub.finance.split.backend.filter;

import com.opencsv.bean.CsvToBeanFilter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FinanceFilter implements CsvToBeanFilter {
  private final String person;

  private final String description;

  @Override
  public boolean allowLine(String[] line) {
    if ("BOTH".equals(person) && "null".equals(description)) {
      return true;
    }
    if (!"BOTH".equals(person)) {
      boolean cendrine = isCendrine(line[2]);
      if ("null".equals(description)) {
        return cendrine;
      } else {
        return cendrine && line[4].contains(description);
      }
    }
    if (!"null".equals(description)) {
      return line[4].contains(description);
    }
    return false;
  }

  private boolean isCendrine(String line) {
    return Boolean.parseBoolean(line) == "CENDRINE".equals(person);
  }
}
