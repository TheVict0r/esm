package com.epam.esm.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Module CORE Spring configuration */
@Configuration
public class CoreConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
