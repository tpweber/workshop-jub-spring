package com.jambit.workshop.jib.spring.data.jdbc.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jambit.workshop.jib.spring.data.jdbc.dao.CustomerDao;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;
import com.jambit.workshop.jib.spring.data.jdbc.domain.EmailAddress;

/**
 * Tests for <code>ConsumerDao</code>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/testApplicationContext.xml" })
public class CostumerDaoTest extends AbstractJUnit4SpringContextTests {

  @Test
  public void testApplicationContext() throws Exception {
    assertNotNull(applicationContext);
    CustomerDao customerDao = getCustomerDao();
    assertNotNull(customerDao);
  }

  private CustomerDao getCustomerDao() {
    return (CustomerDao) applicationContext.getBean("customerDao");
  }

  @Test
  public void testAddCustomer() {
    Customer customer1 = new Customer();
    customer1.withEmailAddress(new EmailAddress(LocalDateTime.now().toString()
        .concat("@addCustomer1.xx")));
    customer1.withName("addCustomer1_firstname", "addCustomer1_lastname");
    assertFalse(customer1.getOptionalId().isPresent());
    final Customer addedCustomer1 = getCustomerDao().addCustomer(customer1);
    assertNotNull(addedCustomer1);
    assertTrue(addedCustomer1.getOptionalId().isPresent());
    assertEquals(customer1.getFirstName(), addedCustomer1.getFirstName());
    assertEquals(customer1.getLastName(), addedCustomer1.getLastName());
    assertEquals(customer1.getEmailAddress(), addedCustomer1.getEmailAddress());
  }

  @Test
  public void testSelectCustomerById() {
    Customer customer1 = new Customer();
    customer1.withEmailAddress(new EmailAddress(LocalDateTime.now().toString()
        .concat("@selectCustomerById1.xx")));
    customer1.withName("selectCustomerById1_firstname",
        "selectCustomerById1_lastname");
    assertFalse(customer1.getOptionalId().isPresent());
    final Customer addedCustomer1 = getCustomerDao().addCustomer(customer1);
    assertNotNull(addedCustomer1);
    assertTrue(addedCustomer1.getOptionalId().isPresent());
    final Long id = addedCustomer1.getOptionalId().get();
    final Customer selectedCustomer = getCustomerDao().selectCustomer(id);
    assertNotNull(selectedCustomer);
    assertEquals(addedCustomer1, selectedCustomer);
  }
}
