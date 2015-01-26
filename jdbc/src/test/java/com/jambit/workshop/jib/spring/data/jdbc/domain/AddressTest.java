package com.jambit.workshop.jib.spring.data.jdbc.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Address;
import com.neovisionaries.i18n.CountryCode;

/**
 * Tests for <code>Product</code>
 */
public class AddressTest {
  @Test
  public void testCreation() {
    Address address = new Address();
    assertNotNull(address.getOptionalId());
    assertFalse(address.getOptionalId().isPresent());
    assertNull(address.getCity());
    assertNull(address.getCountry());
    assertNull(address.getStreet());
  }

  @Test
  public void testData() {
    Address address = new Address();
    address.with("testStreet", "testCity", CountryCode.DE);
    assertEquals("testStreet", address.getStreet());
    assertEquals("testCity", address.getCity());
    assertEquals(CountryCode.DE, address.getCountry());
    address.with("", "", null);
    assertEquals("", address.getStreet());
    assertEquals("", address.getCity());
    assertNull(address.getCountry());
  }

  @Test
  public void testEqualsHashCode() {
    Address address1 = new Address();
    address1.with("testStreet", "testCity", CountryCode.DE);
    Address address2 = new Address();
    address2.with("testStreet", "testCity", CountryCode.DE);
    assertEquals(address1, address2);
    assertEquals(address1.hashCode(), address2.hashCode());
    Address address3 = new Address(1L);
    address3.with("testStreet", "testCity", CountryCode.DE);
    assertNotEquals(address1, address3);
    assertNotEquals(address1.hashCode(), address3.hashCode());
  }
}
