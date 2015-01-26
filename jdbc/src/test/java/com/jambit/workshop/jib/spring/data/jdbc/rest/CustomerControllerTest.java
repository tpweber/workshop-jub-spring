package com.jambit.workshop.jib.spring.data.jdbc.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;
import com.jambit.workshop.jib.spring.data.jdbc.domain.EmailAddress;
import com.jambit.workshop.jib.spring.data.jdbc.rest.CustomerController;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;
import com.jambit.workshop.jib.spring.data.shared.exception.InvalidParameterException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/testApplicationContext.xml" })
@WebAppConfiguration
public class CustomerControllerTest extends AbstractJUnit4SpringContextTests {
  @Autowired
  @Qualifier("customerController")
  CustomerController mController;

  @Test
  public void testGetExistingCustomer() {
    Customer customer = mController.getCustomer(1L);
    assertNotNull(customer);
    assertTrue(customer.hasValidId());
    assertEquals(Long.valueOf(1L), customer.getOptionalId().get());
    assertEquals("test1_firstname", customer.getFirstName());
    assertEquals("test1_lastname", customer.getLastName());
    assertEquals(new EmailAddress("test1@test.de"), customer.getEmailAddress());
    assertEquals("test1_lastname_test1_firstname", customer.getNormalizedName());
  }
  
  @Test(expected = EntityNotFoundException.class)
  public void testGetNotExistingCustomer() {
    mController.getCustomer(Long.MAX_VALUE);
  }

  @Test(expected = InvalidParameterException.class)
  public void testGetCustomerWithInvalidId() {
    mController.getCustomer(-1L);
  }

}
