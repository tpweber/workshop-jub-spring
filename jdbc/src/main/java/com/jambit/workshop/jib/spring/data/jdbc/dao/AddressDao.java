package com.jambit.workshop.jib.spring.data.jdbc.dao;

import java.util.List;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Address;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;

/**
 * DAO for <code>Address</code>
 */
public interface AddressDao {
  Address addAddress(Address address, Customer customer);

  Address selectAddress(long id);

  List<Address> findAddressesByCustomer(Customer customer);
}
