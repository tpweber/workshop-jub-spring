package com.jambit.workshop.jib.spring.data.jdbc.dao;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;

/**
 * DAO for <code>Customer</code>
 */
public interface CustomerDao {

  Customer addCustomer(Customer customer);

  Customer selectCustomer(long id);
}
