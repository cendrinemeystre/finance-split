package com.nexushub.finance.split.backend.controller;

import com.nexushub.finance.split.backend.api.StatsDto;
import com.nexushub.finance.split.backend.service.StatsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class StatsController {
  private StatsService statsService;

  @GetMapping("/stats")
  public StatsDto getStats() {
    return statsService.getStats();
  }
}
