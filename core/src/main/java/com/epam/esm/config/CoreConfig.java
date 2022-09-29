package com.epam.esm.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Module CORE Spring configuration */
@Configuration
@EnableJpaRepositories("com.epam.esm.dao.repositories")
public class CoreConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
