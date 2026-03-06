package com.nexushub.finance.split.backend.controller;

import com.nexushub.finance.split.backend.api.SplitDto;
import com.nexushub.finance.split.backend.service.SplitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class SplitController {
  private final SplitService splitService;

  @GetMapping("/all")
  public SplitDto[] findAll() {
    return splitService.findAll();
  }

  @GetMapping("/filter/{person}/{description}")
  public SplitDto[] findByPerson(@PathVariable("person") String person,
                                 @PathVariable("description") String description) {
    return splitService.findBy(person, description);
  }

  @PostMapping("/add")
  public SplitDto[] addSplit(@RequestBody SplitDto splitDto) {
    splitDto.setId(UUID.randomUUID());
    splitDto.setLocalDateTime(LocalDateTime.now());
    splitService.addSplit(splitDto);
    return splitService.findAll();
  }

  @DeleteMapping("/remove/{id}")
  public void removeSplit(@PathVariable("id") String id) {
    splitService.removeSplit(UUID.fromString(id));
  }
}
