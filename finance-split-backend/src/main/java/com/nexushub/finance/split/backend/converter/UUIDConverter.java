package com.nexushub.finance.split.backend.converter;

import com.opencsv.bean.AbstractBeanField;

import java.util.UUID;

public class UUIDConverter extends AbstractBeanField<UUID, String> {
  @Override
  protected UUID convert(String value) {
    return value == null || value.isBlank() ? null : UUID.fromString(value);
  }
}