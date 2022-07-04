package com.epam.esm.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Basic class for module CORE configuration */
@Configuration
public class CoreConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
