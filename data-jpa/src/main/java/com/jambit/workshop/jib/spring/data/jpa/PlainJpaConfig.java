package com.jambit.workshop.jib.spring.data.jpa;

import com.jambit.workshop.jib.spring.data.jpa.domain.Customer;
import com.jambit.workshop.jib.spring.data.jpa.repo.CustomerRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * JavaConfig class to activate component scanning.
 */
@Configuration
@ComponentScan(basePackageClasses = {Customer.class, CustomerRepository.class})
@Import(InfrastructureConfig.class)
public class PlainJpaConfig {

}
