package com.epam.esm.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Basic class for module CORE configuration
 */
@Configuration
@EnableTransactionManagement
public class CoreConfig {

    @Bean
    public KeyHolder keyHolder() {
        return new GeneratedKeyHolder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}