package com.nexushub.finance.split.backend.filter;

import com.opencsv.bean.CsvToBeanFilter;

public class DescriptionFilter implements CsvToBeanFilter {
  private final String description;

  public DescriptionFilter(String description) {
    this.description = description;
  }

  @Override
  public boolean allowLine(String[] line) {
    return line[4].contains(description);
  }
}
