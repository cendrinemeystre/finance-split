package com.nexushub.finance.split.backend.service;

import com.nexushub.finance.split.backend.api.SplitTotalDto;
import com.nexushub.finance.split.backend.api.StatsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatsService {
  private CsvService csvService;

  public StatsDto getStats() {
    StatsDto dto = new StatsDto();
    List<SplitTotalDto> splitDtos = csvService.readFileForTotal();
    for (SplitTotalDto splitDto : splitDtos) {
      dto.setTotal(getSum(dto.getTotal(), splitDto.getAmount()));
      if (splitDto.isPerson()) {
        dto.setCendrine(getSum(dto.getCendrine(), splitDto.getAmount()));
      } else {
        dto.setPatrick(getSum(dto.getPatrick(), splitDto.getAmount()));
      }
    }
    return dto;
  }

  private double getSum(double total, double amount) {
    return Double.sum(total, amount);
  }
}
