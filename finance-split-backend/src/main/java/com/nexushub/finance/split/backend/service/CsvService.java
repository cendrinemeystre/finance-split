package com.nexushub.finance.split.backend.service;

import com.nexushub.finance.split.backend.api.SplitDto;
import com.nexushub.finance.split.backend.api.SplitTotalDto;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBeanFilter;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class CsvService {
  @Value("${base.dir}")
  public String baseDir;

  @Value("${file.data:data.csv}")
  public File dataFileName;

  @Value("${file.temp:temp.csv}")
  public String tempFile;

  private File dataFile;

  private static final Logger LOG = LoggerFactory.getLogger(CsvService.class);

  @PostConstruct
  public void init() {
    // ensure directory exists
    try {
      Path DATA_FILE = Path.of(baseDir + "/" + dataFileName);
      LOG.info("File: {}", DATA_FILE);
      Files.createDirectories(DATA_FILE.getParent());
      LOG.info("Created/Checked if the Directories exist");

      // create file if it does not exist
      if (!Files.exists(DATA_FILE)) {
        LOG.info("The file does not exist, trying to create the File");
        Files.createFile(DATA_FILE);
        dataFile = DATA_FILE.toFile();
      } else {
        dataFile = DATA_FILE.toFile();
        LOG.info("The File already exists: {}", dataFile.toPath());
      }
      LOG.info("Absolut Path: {}",dataFile.getAbsolutePath());
    } catch (Exception e) {
      LOG.error("Message: {}", e.getMessage());
      LOG.error("Cause: {}", e.getCause().toString());
      for (StackTraceElement el: e.getStackTrace()) {
        LOG.error(el.toString());
      }
    }
  }

  public void addDto(SplitDto splitDto) {
    // Write data to CSV file using CSVWriter
    try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(dataFile, true))
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
    try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(tempFile))
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
      if (dataFile.delete()) {
        File temp = new File(tempFile);
        if (!temp.renameTo(dataFile)) {
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
    try (Reader reader = Files.newBufferedReader(dataFile.toPath())) {
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
    try (Reader reader = Files.newBufferedReader(dataFile.toPath())) {
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
