package com.ibby.hub.finance.split.openapi.spec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibby.hub.finance.split.rest.config.RestControllerConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.webmvc.api.OpenApiResource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Locale;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootApplication
@Import({RestControllerConfig.class})
@ComponentScan(basePackages = {"com.ibby.hub.finance.split"})
public class OpenAPISpecGenerator implements ApplicationListener<ContextRefreshedEvent> {
  private final OpenApiResource openApiResource;

  public OpenAPISpecGenerator(OpenApiResource openApiResource) {
    this.openApiResource = openApiResource;
  }

  public static void main(String[] args) {
    new SpringApplicationBuilder(OpenAPISpecGenerator.class) //
      .contextFactory(type -> new OpenAPISpecApplicationContext()).run(args);
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080"));
    try {
      openApiResource.openapiJson(request, "/v3/api-docs", Locale.getDefault());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
