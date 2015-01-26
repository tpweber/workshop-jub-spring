package com.jambit.workshop.jib.spring.data.jdbc.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jambit.workshop.jib.spring.data.jdbc.dao.AddressDao;
import com.jambit.workshop.jib.spring.data.jdbc.dao.CustomerDao;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Address;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;
import com.jambit.workshop.jib.spring.data.jdbc.domain.EmailAddress;
import com.neovisionaries.i18n.CountryCode;

/**
 * Tests for <code>ConsumerDao</code>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/testApplicationContext.xml" })
public class AddressDaoTest extends AbstractJUnit4SpringContextTests {

  @Autowired
  AddressDao addressDao;

  @Autowired
  CustomerDao customerDao;

  @Test
  public void testApplicationContext() throws Exception {
    assertNotNull(applicationContext);
    assertNotNull(addressDao);
    assertNotNull(customerDao);
  }

  @Test
  public void testAddAddress() {
    Customer customer = new Customer();
    customer.withEmailAddress(new EmailAddress(LocalDateTime.now().toString()
        .concat("@addAddress.xx")));
    customer.withName("addAddress_firstname", "addAddress_lastname");
    final Customer addedCustomer = customerDao.addCustomer(customer);
    assertNotNull(addedCustomer);
    assertTrue(addedCustomer.getOptionalId().isPresent());
    Address address = new Address();
    address.with("addAddress_Street", "addAddress_City", CountryCode.US);
    assertFalse(address.getOptionalId().isPresent());
    final Address addedAddress = addressDao.addAddress(address, addedCustomer);
    assertNotNull(addedAddress);
    assertTrue(addedAddress.getOptionalId().isPresent());
    assertEquals(address.getCity(), addedAddress.getCity());
    assertEquals(address.getCountry(), addedAddress.getCountry());
    assertEquals(address.getStreet(), addedAddress.getStreet());
  }

  @Test
  public void testSelectAddressById() {
    Customer customer = new Customer();
    customer.withEmailAddress(new EmailAddress(LocalDateTime.now().toString()
        .concat("@selectAddressById.xx")));
    customer.withName("selectAddressById_firstname",
        "selectAddressById_lastname");
    final Customer addedCustomer = customerDao.addCustomer(customer);
    assertNotNull(addedCustomer);
    assertTrue(addedCustomer.getOptionalId().isPresent());
    Address address = new Address();
    address.with("selectAddressById_Street", "selectAddressById_City",
        CountryCode.FR);
    assertFalse(address.getOptionalId().isPresent());
    final Address addedAddress = addressDao.addAddress(address, addedCustomer);
    assertNotNull(addedAddress);
    assertTrue(addedAddress.getOptionalId().isPresent());
    final Long id = addedAddress.getOptionalId().get();
    assertTrue(id.intValue() > 0);
    final Address selectedAddress = addressDao.selectAddress(id);
    assertNotNull(selectedAddress);
    assertEquals(addedAddress, selectedAddress);
  }

  @Test
  public void testFindAddressesByCustomer() {
    Customer customer = new Customer();
    customer.withEmailAddress(new EmailAddress(LocalDateTime.now().toString()
        .concat("@findAddressesByCustomer.xx")));
    customer.withName("findAddressesByCustomer_firstname",
        "findAddressesByCustomer_lastname");
    final Customer addedCustomer = customerDao.addCustomer(customer);
    assertNotNull(addedCustomer);
    assertTrue(addedCustomer.getOptionalId().isPresent());
    Address address1 = new Address();
    address1.with("findAddressesByCustomer_Street_1",
        "findAddressesByCustomer_City", CountryCode.DK);
    assertFalse(address1.getOptionalId().isPresent());
    final Address addedAddress1 = addressDao
        .addAddress(address1, addedCustomer);
    assertNotNull(addedAddress1);
    assertTrue(addedAddress1.getOptionalId().isPresent());
    final Long id1 = addedAddress1.getOptionalId().get();
    assertTrue(id1.intValue() > 0);
    final List<Address> foundAddresses1 = addressDao
        .findAddressesByCustomer(addedCustomer);
    assertNotNull(foundAddresses1);
    assertEquals(1, foundAddresses1.size());
    assertTrue(foundAddresses1.contains(addedAddress1));
    Address address2 = new Address();
    address2.with("findAddressesByCustomer_Street_2",
        "findAddressesByCustomer_City", CountryCode.DK);
    assertFalse(address2.getOptionalId().isPresent());
    final Address addedAddress2 = addressDao
        .addAddress(address2, addedCustomer);
    assertNotNull(addedAddress2);
    assertTrue(addedAddress2.getOptionalId().isPresent());
    final Long id2 = addedAddress2.getOptionalId().get();
    assertTrue(id2.intValue() > 0);
    final List<Address> foundAddresses2 = addressDao
        .findAddressesByCustomer(addedCustomer);
    assertNotNull(foundAddresses2);
    assertEquals(2, foundAddresses2.size());
    assertTrue(foundAddresses2.contains(addedAddress1));
    assertTrue(foundAddresses2.contains(addedAddress2));
  }
}
