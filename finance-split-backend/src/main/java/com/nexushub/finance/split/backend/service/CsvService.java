package com.nexushub.finance.split.backend.service;

import com.nexushub.finance.split.backend.api.SplitDto;
import com.nexushub.finance.split.backend.api.SplitTotalDto;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBeanFilter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Service
public class CsvService {
  private static final String BASE = System.getProperty("user.home") + "/finance-split/";

  private static final File DB_FILE = new File(BASE + "db.csv");

  private static final String TEMP_FILE = BASE + "temp.csv";

  public CsvService() {
    DB_FILE.mkdir();
  }

  public void addDto(SplitDto splitDto) {
    // Write data to CSV file using CSVWriter
    try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(DB_FILE, true))
      .withSeparator(';')
      .build()) {
      writer.writeNext(new String[]{
        splitDto.getId().toString(),
        splitDto.getLocalDateTime().toString(),
        String.valueOf(splitDto.isPerson()),
        String.valueOf(splitDto.getAmount()),
        splitDto.getDescription()
      });
    } catch (IOException e) {
      throw new RuntimeException("Failed to write data to CSV file", e);
    }
  }

  public void removeDto(UUID id) {
    List<SplitDto> dtoList = readFile();
    dtoList.removeIf(splitDto -> splitDto.getId().toString().equals(id.toString()));
    try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(TEMP_FILE))
      .withSeparator(';')
      .withQuoteChar('"')
      .build()) {
      writer.writeNext(new String[]{"id", "localDateTime", "person", "amount", "description"});
      for (SplitDto splitDto : dtoList) {
        String[] dto = {
          splitDto.getId().toString(),
          splitDto.getLocalDateTime().toString(),
          String.valueOf(splitDto.isPerson()),
          String.valueOf(splitDto.getAmount()),
          splitDto.getDescription()
        };
        writer.writeNext(dto, true);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to remove data from CSV file", e);
    }

    // Replace original file with updated temp file
    try {
      if (DB_FILE.delete()) {
        File temp = new File(TEMP_FILE);
        if (!temp.renameTo(DB_FILE)) {
          throw new RuntimeException("Failed to rename temp file to the original DB file.");
        }
      } else {
        throw new RuntimeException("Error deleting the original file.");
      }
    } catch (Exception e) {
      throw new RuntimeException("Error processing file replacement", e);
    }
  }

  public List<SplitDto> readFile() {
    return readFile(null);
  }

  public List<SplitDto> readFile(CsvToBeanFilter filter) {
    // Read CSV file and return list of SplitDto objects
    try (Reader reader = Files.newBufferedReader(DB_FILE.toPath())) {
      CsvToBeanBuilder<SplitDto> builder = new CsvToBeanBuilder<SplitDto>(reader)
        .withType(SplitDto.class)
        .withSeparator(';')
        .withIgnoreEmptyLine(true);

      if (filter != null) {
        builder.withFilter(filter); // Apply filter if provided
      }

      CsvToBean<SplitDto> csvToBean = builder.build();
      return csvToBean.parse();
    } catch (IOException e) {
      throw new RuntimeException("Failed to read data from CSV file", e);
    }
  }

  public List<SplitTotalDto> readFileForTotal() {
    return readFileForTotal(null);
  }

  public List<SplitTotalDto> readFileForTotal(CsvToBeanFilter filter) {
    // Read CSV file and return list of SplitDto objects
    try (Reader reader = Files.newBufferedReader(DB_FILE.toPath())) {
      CsvToBeanBuilder<SplitTotalDto> builder = new CsvToBeanBuilder<SplitTotalDto>(reader)
        .withType(SplitTotalDto.class)
        .withSeparator(';')
        .withIgnoreEmptyLine(true);

      if (filter != null) {
        builder.withFilter(filter); // Apply filter if provided
      }

      CsvToBean<SplitTotalDto> csvToBean = builder.build();
      return csvToBean.parse();
    } catch (IOException e) {
      throw new RuntimeException("Failed to read data from CSV file", e);
    }
  }
}
