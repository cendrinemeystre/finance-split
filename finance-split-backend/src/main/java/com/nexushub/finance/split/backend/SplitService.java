package com.nexushub.finance.split.backend;

import com.nexushub.finance.split.backend.filter.DescriptionFilter;
import com.nexushub.finance.split.backend.filter.PersonFilter;
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

  public SplitDto[] findByPerson(boolean cendrine) {
    return csvService.readFile(new PersonFilter(cendrine)).toArray(new SplitDto[0]);
  }

  public SplitDto[] findByDescription(String description) {
    return csvService.readFile(new DescriptionFilter(description)).toArray(new SplitDto[0]);
  }

  public void addSplit(SplitDto splitDto) {
    csvService.addDto(splitDto);
  }

  public void removeSplit(UUID id) {
    csvService.removeDto(id);
  }
}
