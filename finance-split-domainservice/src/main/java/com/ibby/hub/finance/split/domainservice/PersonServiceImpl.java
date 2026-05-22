package com.ibby.hub.finance.split.domainservice;

import com.ibby.hub.finance.split.api.PersonDto;
import com.ibby.hub.finance.split.api.PersonService;
import com.ibby.hub.finance.split.domain.Person;
import com.ibby.hub.finance.split.domainservice.converter.PersonConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {
  private final PersonRepository personRepository;

  private final PersonConverter personConverter;

  public PersonServiceImpl(PersonRepository repository, PersonConverter converter) {
    personRepository = repository;
    personConverter = converter;
  }

  @Override
  public PersonDto[] findAll() {
    return personRepository.findAll().stream()
      .map(personConverter::convertToDto)
      .toArray(PersonDto[]::new);
  }

  @Override
  public PersonDto createPerson(PersonDto personDto) {
    Person person = personConverter.convertToEntity(personDto);
    Person save = personRepository.save(person);
    return personConverter.convertToDto(save);
  }

  @Override
  public PersonDto updatePerson(String id, PersonDto personDto) {
    Optional<Person> optionalPerson = personRepository.findById(UUID.fromString(id));
    if (optionalPerson.isPresent()) {
      Person person = optionalPerson.get();
      if (!person.getName().equals(personDto.getName())) {
        person.setName(personDto.getName());
      }
      if (person.getTotal() != personDto.getTotal()) {
        person.setTotal(personDto.getTotal());
      }
      Person save = personRepository.save(person);
      return personConverter.convertToDto(save);
    } else {
      throw new EntityNotFoundException();
    }
  }

  @Override
  public void updateAddTotalFor(UUID id, double add) {
    personRepository.updateAddTotalFor(add, id);
  }

  @Override
  public void updateSubtractTotalFor(UUID id, double subtract) {
    personRepository.updateSubtractTotalFor(subtract, id);
  }

  @Override
  public void deletePerson(String id) {
    personRepository.deleteById(UUID.fromString(id));
  }

  @Override
  public PersonDto findPerson(String id) {
    return personConverter.convertToDto(personRepository.findById(UUID.fromString(id))
      .orElseThrow(EntityNotFoundException::new));
  }
}
