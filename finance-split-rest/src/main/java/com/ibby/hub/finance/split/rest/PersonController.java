package com.ibby.hub.finance.split.rest;

import com.ibby.hub.finance.split.api.PersonDto;
import com.ibby.hub.finance.split.api.PersonService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {
  private final PersonService personService;

  public PersonController(PersonService service) {personService = service;}

  @GetMapping("/all")
  public PersonDto[] findAll() {
    return personService.findAll();
  }

  @PostMapping("/create")
  public PersonDto createPerson(@RequestBody PersonDto personDto) {
    return personService.createPerson(personDto);
  }

  @PutMapping("/{id}")
  public PersonDto updatePerson(@PathVariable("id") String id, @RequestBody PersonDto personDto) {
    return personService.updatePerson(id, personDto);
  }

  @DeleteMapping("/delete/{id}")
  public void deletePerson(@PathVariable("id") String id) {
    personService.deletePerson(id);
  }
}
