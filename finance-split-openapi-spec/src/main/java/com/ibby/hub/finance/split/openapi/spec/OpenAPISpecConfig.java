package com.ibby.hub.finance.split.openapi.spec;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPISpecConfig {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().components(new Components().addSecuritySchemes("Bearer",
      new SecurityScheme().type(Type.HTTP).scheme("Bearer").bearerFormat("JWT").in(In.HEADER)));
  }
}
