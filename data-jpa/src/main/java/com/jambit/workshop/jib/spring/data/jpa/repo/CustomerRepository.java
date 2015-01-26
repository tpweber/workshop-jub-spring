package com.jambit.workshop.jib.spring.data.jpa.repo;

import com.jambit.workshop.jib.spring.data.jpa.domain.Customer;
import com.jambit.workshop.jib.spring.data.jpa.domain.EmailAddress;
import org.springframework.data.repository.Repository;

/**
 * Repository for Customer.
 */
public interface CustomerRepository extends Repository<Customer, Long> {

    Customer findOne(Long id);

    Customer save(Customer customer);

    Customer findByEmailAddress(EmailAddress emailAddress);
}
