package com.ibby.hub.finance.split.api;

import java.util.UUID;

public interface PersonService {
  PersonDto[] findAll();

  PersonDto createPerson(PersonDto personDto);

  PersonDto updatePerson(String id, PersonDto personDto);

  void updateAddTotalFor(UUID id, double add);

  void updateSubtractTotalFor(UUID id, double subtract);

  void deletePerson(String id);

  PersonDto findPerson(String id);
}
