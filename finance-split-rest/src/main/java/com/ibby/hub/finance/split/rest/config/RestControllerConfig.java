package com.ibby.hub.finance.split.rest.config;

import com.ibby.hub.finance.split.rest.PublicApiPath;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.ibby.hub.finance.split")
public class RestControllerConfig implements WebMvcConfigurer {
  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.addPathPrefix(PublicApiPath.PATH_PREFIX,
      clazz -> clazz.getPackageName().startsWith(PublicApiPath.class.getPackageName()));
    configurer.getPatternParserOrDefault();
  }
}