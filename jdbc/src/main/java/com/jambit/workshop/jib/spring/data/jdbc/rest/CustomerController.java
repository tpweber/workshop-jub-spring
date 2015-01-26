package com.jambit.workshop.jib.spring.data.jdbc.rest;

import static com.jambit.workshop.jib.spring.data.shared.rest.RestPathConstants.PATH_CUSTOMER_ID;
import static com.jambit.workshop.jib.spring.data.shared.rest.RestPathConstants.VARIABLE_ID;
import static com.jambit.workshop.jib.spring.data.shared.domain.Predicates.isValidId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;
import com.jambit.workshop.jib.spring.data.jdbc.manager.CustomerManager;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;
import com.jambit.workshop.jib.spring.data.shared.exception.InvalidParameterException;
/**
 * Controller for REST
 */
@RestController("customerController")
public class CustomerController {

  @Autowired
  @Qualifier("customerManager")
  CustomerManager mCustomerManager;

  /**
   * Returns the customer for the given id. 
   * @param id the id of a customer.
   * @return the customer
   * @exception EntityNotFoundException if the id is valid, but no customer with this id exists
   * @exception InvalidParameterException if the id is not valid.  
   */
  @RequestMapping(value= PATH_CUSTOMER_ID, method=RequestMethod.GET)
  public Customer getCustomer(@PathVariable(VARIABLE_ID) long id) {
    if(isValidId().test(id)) {
      final Customer customer = mCustomerManager.getCustomer(id);
      if(null != customer) {
        return customer;
      }
      throw new EntityNotFoundException("Customer not found for id[" + id + "].");
    }
    throw new InvalidParameterException("Customer invalid id[" + id + "].");
  }
}
