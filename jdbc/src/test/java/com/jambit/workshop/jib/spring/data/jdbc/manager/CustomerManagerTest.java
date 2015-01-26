package com.jambit.workshop.jib.spring.data.jdbc.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;
import com.jambit.workshop.jib.spring.data.jdbc.domain.EmailAddress;
import com.jambit.workshop.jib.spring.data.jdbc.manager.CustomerManager;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;

/**
 * Tests for <code>CustomerManager</code>.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/testApplicationContext.xml" })
public class CustomerManagerTest extends AbstractJUnit4SpringContextTests {

  @Autowired
  CustomerManager customerManager;

  @Test
  public void testApplicationContext() throws Exception {
    assertNotNull(applicationContext);
    assertNotNull(customerManager);
  }

  @Test
  public void testGetCustomer() {
    Customer customer = customerManager.getCustomer(1);
    assertNotNull(customer);
    assertEquals("test1_firstname", customer.getFirstName());
    assertEquals("test1_lastname", customer.getLastName());
    assertEquals(new EmailAddress("test1@test.de"), customer.getEmailAddress());
    assertEquals("test1_lastname_test1_firstname", customer.getNormalizedName());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testGetCustomerWithInvalidId() {
    customerManager.getCustomer(-1L);
  }

}
