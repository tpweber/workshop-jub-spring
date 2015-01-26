package com.jambit.workshop.jib.spring.data.jdbc.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.longThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.internal.matchers.GreaterThan;
import org.mockito.internal.matchers.LessOrEqual;
import org.springframework.dao.DataRetrievalFailureException;

import com.jambit.workshop.jib.spring.data.jdbc.dao.CustomerDao;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;
import com.jambit.workshop.jib.spring.data.jdbc.domain.EmailAddress;
import com.jambit.workshop.jib.spring.data.jdbc.manager.CustomerManager;
import com.jambit.workshop.jib.spring.data.jdbc.manager.impl.CustomerManagerImpl;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;

/**
 * Tests for <code>CustomerManager</code>, based on mocked methods.
 */
public class MockCustomerManagerTest {

  @SuppressWarnings("static-method")
  @Test
  public void testGetCustomer() {
    CustomerManager customerManager = createCustomerManager(8L);
    Customer customer = customerManager.getCustomer(8L);
    assertNotNull(customer);
    assertEquals("firstName_8", customer.getFirstName());
    assertEquals("lastName_8", customer.getLastName());
    assertEquals(new EmailAddress("8@email.de"), customer.getEmailAddress());
    assertEquals("lastName_8_firstName_8", customer.getNormalizedName());
  }

  @SuppressWarnings("static-method")
  @Test(expected = EntityNotFoundException.class)
  public void testGetCustomerWithInvalidId() {
    CustomerManager customerManager = createCustomerManager(-1L);
    customerManager.getCustomer(-1L);
  }

  private static CustomerManager createCustomerManager(long id) {
    CustomerManagerImpl customerManager = new CustomerManagerImpl();
    customerManager.setCustomerDao(mockCustomerDao(id));
    return customerManager;
  }

  private static CustomerDao mockCustomerDao(long id) {
    CustomerDao mocked = mock(CustomerDao.class);
    when(mocked.selectCustomer(longThat(new GreaterThan<>(0L)))).thenReturn(
        createCustomer(id));
    doThrow(DataRetrievalFailureException.class).when(mocked).selectCustomer(
        longThat(new LessOrEqual<>(0L)));
    return mocked;
  }

  private static Customer createCustomer(long id) {
    Customer customer = new Customer(id);
    customer.withName("firstName_" + id, "lastName_" + id);
    customer.withEmailAddress(new EmailAddress(id + "@email.de"));
    return customer;
  }
}
