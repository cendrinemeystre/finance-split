package com.nexushub.finance.split.backend.service;

import com.nexushub.finance.split.backend.api.SplitDto;
import com.nexushub.finance.split.backend.filter.FinanceFilter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SplitService {
  private final CsvService csvService;

  public SplitDto[] findAll() {
    return csvService.readFile().toArray(new SplitDto[0]);
  }

  public SplitDto[] findByPerson(String person, String description) {
    return csvService.readFile(new FinanceFilter(person.toUpperCase(), description)).toArray(new SplitDto[0]);
  }

  public void addSplit(SplitDto splitDto) {
    csvService.addDto(splitDto);
  }

  public void removeSplit(UUID id) {
    csvService.removeDto(id);
  }
}
