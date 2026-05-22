package com.ibby.hub.finance.split.openapi.spec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class OpenAPISpecSecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) {
    http.authorizeHttpRequests(a -> {
      a.requestMatchers("/v3/api-docs").permitAll();
      a.requestMatchers("/**").denyAll();
    });
    return http.build();
  }
}
