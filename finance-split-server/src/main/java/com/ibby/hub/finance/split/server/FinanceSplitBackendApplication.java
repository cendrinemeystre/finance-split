package com.ibby.hub.finance.split.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ibby.hub.finance.split")
@EntityScan(basePackages = "com.ibby.hub.finance.split.domain")
public class FinanceSplitBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(FinanceSplitBackendApplication.class, args);
  }

}
