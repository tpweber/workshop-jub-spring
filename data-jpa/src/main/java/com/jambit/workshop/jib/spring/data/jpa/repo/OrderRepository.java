package com.jambit.workshop.jib.spring.data.jpa.repo;

import com.jambit.workshop.jib.spring.data.jpa.domain.Customer;
import com.jambit.workshop.jib.spring.data.jpa.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Repository for Orders.
 */
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> findByCustomer(Customer customer);
}
