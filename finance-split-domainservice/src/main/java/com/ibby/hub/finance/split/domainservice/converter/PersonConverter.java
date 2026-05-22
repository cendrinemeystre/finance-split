package com.ibby.hub.finance.split.domainservice.converter;

import com.ibby.hub.finance.split.api.PersonDto;
import com.ibby.hub.finance.split.domain.Person;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PersonConverter {
  public Person convertToEntity(PersonDto personDto) {
    Person.Builder builder = Person.builder()
      .name(personDto.getName())
      .total(personDto.getTotal());
    if (personDto.getId() != null) {
      builder.id(personDto.getId());
    } else {
      builder.id(UUID.randomUUID());
    }
    return builder.build();
  }

  public PersonDto convertToDto(Person person) {
    PersonDto.Builder builder = PersonDto.builder()
      .name(person.getName())
      .total(person.getTotal());
    if (person.getId() != null) {
      builder.id(person.getId());
    } else {
      builder.id(UUID.randomUUID());
    }
    return builder.build();
  }
}
