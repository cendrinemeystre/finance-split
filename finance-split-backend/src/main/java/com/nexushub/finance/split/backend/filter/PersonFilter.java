package com.nexushub.finance.split.backend.filter;

import com.opencsv.bean.CsvToBeanFilter;

public class PersonFilter implements CsvToBeanFilter {
  private final boolean cendrine;

  public PersonFilter(boolean cendrine) {
    this.cendrine = cendrine;
  }

  @Override
  public boolean allowLine(String[] line) {
    return Boolean.parseBoolean(line[2]) == cendrine;
  }
}
