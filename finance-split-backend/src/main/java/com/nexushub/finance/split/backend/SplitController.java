package com.nexushub.finance.split.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController("split")
public class SplitController {
  private final SplitService splitService;

  public SplitController(SplitService service) {
    splitService = service;
  }

  @GetMapping("/all")
  public SplitDto[] findAll() {
    return splitService.findAll();
  }

  @GetMapping("/{person}")
  public SplitDto[] findByPerson(@PathVariable("person") boolean person) {
    return splitService.findByPerson(person);
  }

  @GetMapping("/{description}")
  public SplitDto[] findByDescription(@PathVariable("description") String description) {
    return splitService.findByDescription(description);
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
