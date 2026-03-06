package com.nexushub.finance.split.backend.service;

import com.nexushub.finance.split.backend.api.SplitDto;
import com.nexushub.finance.split.backend.filter.FinanceFilter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SplitService {
  private static final Logger LOG = LoggerFactory.getLogger(SplitService.class);

  private final CsvService csvService;

  public SplitDto[] findAll() {
    SplitDto[] array = csvService.readFile().toArray(new SplitDto[0]);
    LOG.debug("Find All: has Size {}", array.length);
    return array;
  }

  public SplitDto[] findBy(String person, String description) {
    SplitDto[] array = csvService.readFile(new FinanceFilter(person.toUpperCase(), description)).toArray(new SplitDto[0]);
    LOG.debug("Find By: has Size {}", array.length);
    return array;
  }

  public void addSplit(SplitDto splitDto) {
    LOG.info("Adding Entry: {}: {}", splitDto.getAmount(), splitDto.getDescription());
    csvService.addDto(splitDto);
  }

  public void removeSplit(UUID id) {
    LOG.info("Deleting Entry with ID: {}", id.toString());
    csvService.removeDto(id);
  }
}
