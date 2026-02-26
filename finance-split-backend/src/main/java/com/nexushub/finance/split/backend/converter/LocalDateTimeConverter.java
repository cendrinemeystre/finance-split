package com.nexushub.finance.split.backend.converter;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDateTime;

public class LocalDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {
  @Override
  protected LocalDateTime convert(String value) {
    return value == null || value.isBlank()
      ? null
      : LocalDateTime.parse(value);
  }
}
