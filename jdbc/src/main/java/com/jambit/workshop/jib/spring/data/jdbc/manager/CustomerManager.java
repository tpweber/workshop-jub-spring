package com.jambit.workshop.jib.spring.data.jdbc.manager;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;

/**
 * API for Customer-related things.
 */
public interface CustomerManager {
  Customer getCustomer(long id);
}
