package com.ibby.hub.finance.split.domainservice;

import com.ibby.hub.finance.split.api.FinanceDataDto;
import com.ibby.hub.finance.split.api.FinanceService;
import com.ibby.hub.finance.split.api.PersonService;
import com.ibby.hub.finance.split.domain.FinanceData;
import com.ibby.hub.finance.split.domain.Person;
import com.ibby.hub.finance.split.domainservice.converter.FinanceDataConverter;
import com.ibby.hub.finance.split.domainservice.converter.PersonConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FinanceServiceImpl implements FinanceService {
  private final FinanceRepository financeRepository;

  private final PersonService personService;

  private final FinanceDataConverter financeDataConverter;

  private final PersonConverter personConverter;

  public FinanceServiceImpl(FinanceRepository repository, PersonService impl, FinanceDataConverter converter,
                            PersonConverter personConverter) {
    this.financeRepository = repository;
    this.personService = impl;
    this.financeDataConverter = converter;
    this.personConverter = personConverter;
  }

  @Override
  public FinanceDataDto[] findAll() {
    return financeRepository.findAllOrdered().stream()
      .map(financeDataConverter::convertToDto)
      .toArray(FinanceDataDto[]::new);
  }

  @Override
  public FinanceDataDto createFinanceData(FinanceDataDto financeDataDto) {
    FinanceData data = financeDataConverter.convertToEntity(financeDataDto);
    Person person = personConverter.convertToEntity(personService.findPerson(financeDataDto.getPersonId()));
    personService.updateAddTotalFor(person.getId(), financeDataDto.getAmount());
    data.setPerson(person);
    FinanceData save = financeRepository.save(data);
    return financeDataConverter.convertToDto(save);
  }

  @Override
  public void deleteFinanceData(String id) {
    financeRepository.findById(UUID.fromString(id)).ifPresentOrElse(financeData -> {
      personService.updateSubtractTotalFor(financeData.getPerson().getId(), financeData.getAmount());
      financeRepository.deleteById(UUID.fromString(id));
    }, EntityNotFoundException::new);
  }
}
