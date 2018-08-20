package com.asraf;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.asraf.repositories.persistence.ExtendedQueryDslJpaRepositoryImpl;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = ExtendedQueryDslJpaRepositoryImpl.class)
public class SpringConfig {
	@Bean
	@Scope(value = "prototype")
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
