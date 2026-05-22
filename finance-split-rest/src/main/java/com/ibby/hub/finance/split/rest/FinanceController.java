package com.ibby.hub.finance.split.rest;

import com.ibby.hub.finance.split.api.FinanceDataDto;
import com.ibby.hub.finance.split.api.FinanceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/finance", produces = MediaType.APPLICATION_JSON_VALUE)
public class FinanceController {
  private final FinanceService financeService;

  public FinanceController(FinanceService service) {financeService = service;}

  @GetMapping("/all")
  public FinanceDataDto[] findAll() {
    return financeService.findAll();
  }

  @PostMapping("/create")
  public FinanceDataDto createFinanceData(@RequestBody FinanceDataDto financeDataDto) {
    return financeService.createFinanceData(financeDataDto);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteFinanceData(@PathVariable("id") String id) {
    financeService.deleteFinanceData(id);
  }
}
