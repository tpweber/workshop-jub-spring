package com.jambit.workshop.jib.spring.data.mongo.repo;

import com.jambit.workshop.jib.spring.data.mongo.domain.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/testApplicationContext.xml"})
public class CustomerRepositoryTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    @Qualifier("customerRepository")
    CustomerRepository mRepository;

    final DateTimeFormatter mFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS").withZone(ZoneId.systemDefault());

    @Before
    public void setUp() throws Exception {
        assertNotNull(applicationContext);
        assertNotNull(mRepository);
    }

    private String createId() {
        return mFormatter.format(Instant.now());
    }

    @Test
    public void testAddCustomer() {
        final String id = createId();
        Customer customer = new Customer(id);
        customer.withName("testAddCustomer_firstName", "testAddCustomer_lastName").withEmail(mFormatter.format(Instant.now()).concat("@testAddCustomer.email.de"));
        final Customer added = mRepository.save(customer);
        assertNotNull(added);
        assertEquals(customer, added);
        Customer found = mRepository.findOne(id);
        assertNotNull(found);
        assertEquals(customer, found);
    }

    @Test
    public void testFindAll() {
        final String id = createId();
        Customer customer = new Customer(id);
        customer.withName("testAddCustomer_firstName", "testAddCustomer_lastName").withEmail(mFormatter.format(Instant.now()).concat("@testAddCustomer.email.de"));
        final Customer added = mRepository.save(customer);
        assertNotNull(added);
        assertEquals(customer, added);
        List<Customer> found = mRepository.findAll();
        assertNotNull(found);
        assertFalse(found.isEmpty());
        assertTrue(found.contains(customer));
    }
    /*
  @Test
  public void testFindAllSort() {
    final String id1 = createId();
    final String name1 = "testFindAllSort1_name_" + id1;
    Product product1 = new Product(id1);
    product1.withDescription("testFindAllSort1_description").withName(name1).withPriceInCents(1111);
    final Product added1 = mProductRepository.save(product1);
    assertNotNull(added1);
    assertEquals(product1, added1);
    final String id2 = createId();
    final String name2 = "testFindAllSort2_name_" + id2;
    Product product2 = new Product(id2);
    product2.withDescription("testFindAllSort2_description").withName(name2).withPriceInCents(2222);
    final Product added2 = mProductRepository.save(product2);
    assertNotNull(added2);
    assertEquals(product2, added2);
    Sort sortAsc = Sorts.NAME_ASC.getSort();
    List<Product> foundAsc = mProductRepository.findAll(sortAsc);
    assertNotNull(foundAsc);
    assertFalse(foundAsc.isEmpty());
    assertTrue(foundAsc.contains(product1));
    assertTrue(foundAsc.contains(product2));
    assertTrue(foundAsc.indexOf(product1) < foundAsc.indexOf(product2));
    Sort sortDesc = Sorts.NAME_DESC.getSort();
    List<Product> foundDesc = mProductRepository.findAll(sortDesc);
    assertNotNull(foundDesc);
    assertFalse(foundDesc.isEmpty());
    assertTrue(foundDesc.contains(product1));
    assertTrue(foundDesc.contains(product2));
    assertTrue(foundDesc.indexOf(product1) > foundDesc.indexOf(product2));
  }
  */


    @Test
    public void testFindByEmail() {
        final String id = createId();
        final String email = id.concat("@testFindByName.email.de");
        Customer customer = new Customer(id);
        customer.withName("testFindByName_firstName", "testFindByName_lastName").withEmail(email);
        final Customer added = mRepository.save(customer);
        assertNotNull(added);
        assertEquals(customer, added);
        Customer found = mRepository.findByEmail(email);
        assertNotNull(found);
        assertEquals(customer, found);
    }

    @Test
    public void testFindByName() {
        final String id = createId();
        final String email = id.concat("@testFindByName.email.de");
        Customer customer = new Customer(id);
        String lastName = "testFindByName_lastName";
        String firstName = "testFindByName_firstName";
        customer.withName(firstName, lastName).withEmail(email);
        final Customer added = mRepository.save(customer);
        assertNotNull(added);
        assertEquals(customer, added);
        List<Customer> foundCustomers = mRepository.findByFullname(firstName, lastName);
        assertNotNull(foundCustomers);
        assertFalse(foundCustomers.isEmpty());
        assertTrue(foundCustomers.contains(customer));
    }


}
