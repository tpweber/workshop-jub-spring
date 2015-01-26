package com.jambit.workshop.jib.spring.data.jdbc.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;

/**
 * Tests for <code>Customer</code>
 */
public class CustomerTest {
  @Test
  public void testCreation() {
    Customer customer = new Customer();
    assertNotNull(customer.getOptionalId());
    assertFalse(customer.getOptionalId().isPresent());
    assertNull(customer.getEmailAddress());
    assertNull(customer.getFirstName());
    assertNull(customer.getLastName());
    assertNull(customer.getNormalizedName());
  }

  @Test
  public void testNormalizedName() {
    Customer customer = new Customer();
    customer.withName("testVorname", "testNachname");
    assertEquals("testNachname_testVorname", customer.getNormalizedName());
    assertEquals("testN\\u00E4chn\\u00E4me\\u00DF_testV\\u00F6rn\\u00E4me",
        customer.withName("testVörnäme", "testNächnämeß").getNormalizedName());
  }

  @Test
  public void testName() {
    Customer customer = new Customer();
    customer.withName("testVorname", "testNachname");
    assertEquals("testVorname", customer.getFirstName());
    assertEquals("testNachname", customer.getLastName());
    customer.withName("testVörnäme", "testNächnämeß");
    assertEquals("testVörnäme", customer.getFirstName());
    assertEquals("testNächnämeß", customer.getLastName());
  }
}
