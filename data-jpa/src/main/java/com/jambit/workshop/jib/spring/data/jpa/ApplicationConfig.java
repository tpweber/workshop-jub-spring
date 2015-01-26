package com.jambit.workshop.jib.spring.data.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JavaConfig to enable Spring Data JPA repositories.
 */
@Configuration
@EnableJpaRepositories
@Import(InfrastructureConfig.class)
public class ApplicationConfig {

}
