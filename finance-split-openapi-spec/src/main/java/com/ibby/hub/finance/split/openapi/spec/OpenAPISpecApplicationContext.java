package com.ibby.hub.finance.split.openapi.spec;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.server.servlet.context.AnnotationConfigServletWebServerApplicationContext;

import java.beans.Introspector;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class OpenAPISpecApplicationContext extends AnnotationConfigServletWebServerApplicationContext {
  public OpenAPISpecApplicationContext() {
    super(new DefaultListableBeanFactory() {
      @Override
      protected Map<String, Object> findAutowireCandidates(String beanName, Class<?> requiredType,
                                                           DependencyDescriptor descriptor) {
        if (!requiredType.getPackageName().startsWith("com.ibby.hub")) {
          return super.findAutowireCandidates(beanName, requiredType, descriptor);
        }
        String mockBeanName = Introspector.decapitalize(requiredType.getSimpleName()) + "Mock";
        Map<String, Object> autowireCandidates = new HashMap<>();
        try {
          autowireCandidates = super.findAutowireCandidates(beanName, requiredType, descriptor);
        } catch (UnsatisfiedDependencyException e) {
          if (e.getCause() != null && e.getCause().getCause() instanceof NoSuchBeanDefinitionException) {
            mockBeanName = ((NoSuchBeanDefinitionException) e.getCause().getCause()).getBeanName();
          }
          this.registerBeanDefinition(mockBeanName,
            BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition());
        }
        if (autowireCandidates.isEmpty()) {
          final Object mock = mock(requiredType);
          autowireCandidates.put(mockBeanName, mock);
          this.addSingleton(mockBeanName, mock);
        }
        return autowireCandidates;
      }
    });
  }
}
