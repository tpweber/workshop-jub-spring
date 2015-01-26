package com.jambit.workshop.jib.spring.data.mongo.repo;

import com.jambit.workshop.jib.spring.data.mongo.domain.Customer;
import com.jambit.workshop.jib.spring.data.mongo.domain.Order;
import com.jambit.workshop.jib.spring.data.mongo.domain.OrderItem;
import com.jambit.workshop.jib.spring.data.mongo.domain.OrderStatus;
import com.jambit.workshop.jib.spring.data.mongo.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/testApplicationContext.xml"})
public class OrderRepositoryTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    @Qualifier("orderRepository")
    OrderRepository mOrderRepository;

    @Autowired
    @Qualifier("productRepository")
    ProductRepository mProductRepository;

    @Autowired
    @Qualifier("customerRepository")
    CustomerRepository mCustomerRepository;

    Customer mCustomer;

    final DateTimeFormatter mFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS").withZone(ZoneId.systemDefault());

    @Before
    public void setUp() throws Exception {
        assertNotNull(applicationContext);
        assertNotNull(mOrderRepository);
        assertNotNull(mProductRepository);
        assertNotNull(mCustomerRepository);
        mCustomer = createCustomer();
        assertNotNull(mCustomer);
    }

    private Customer createCustomer() {
        String id = createId();
        return mCustomerRepository.save(new Customer(id, "c_firstName", "c_lastName", id.concat("@order.test")));
    }

    private List<Product> findProduct(String name) {
        return mProductRepository.findByName(name);
    }

    private boolean existsProduct(final String name) {
        return !CollectionUtils.isEmpty(findProduct(name));
    }

    private Product createProduct(final String name, final String desc, int price) {
        Product product = new Product().withName(name).withDescription(desc).withPriceInCents(price);
        return mProductRepository.save(product);
    }

    private Product getProduct(final String name, final String desc, int price) {
        if (!existsProduct(name)) {
            return createProduct(name, desc, price);
        }
        return findProduct(name).get(0);
    }

    private String createId() {
        return mFormatter.format(Instant.now());
    }

    @Test
    public void testAddOrder() {
        final String orderId = createId();
        Order order = new Order(orderId, OrderStatus.PENDING).withCustomer(mCustomer);
        Product product1 = getProduct("testAddOrder_name1", "testAddOrder_desc1", 11111);
        Product product2 = getProduct("testAddOrder_name2", "testAddOrder_desc2", 22222);
        OrderItem item1 = new OrderItem(createId(), product1, 1);
        OrderItem item2 = new OrderItem(createId(), product2, 2);
        order.withOrderItems(item1, item2);
        final Order added = mOrderRepository.save(order);
        assertNotNull(added);
        assertEquals(order, added);
        Order found = mOrderRepository.findOne(orderId);
        assertNotNull(found);
        assertEquals(order, found);
    }

  /*@Test
  public void testFindAll() {
    final String id = createId();
    final String name = "testFindAll_name_" + id;
    Product product = new Product(id);
    product.withDescription("testFindAll_description").withName(name).withPriceInCents(2222);
    final Product added = mProductRepository.save(product);
    assertNotNull(added);
    assertEquals(product, added);
    List<Product> found = mProductRepository.findAll();
    assertNotNull(found);
    assertFalse(found.isEmpty());
    assertTrue(found.contains(product));
  }
  
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
  
  

  @Test
  public void testFindByName() {
    final String id = createId();
    final String tName = "testFindByName_name_" + id;
    Product product = new Product(id);
    product.withDescription("testFindByName_description").withName(tName).withPriceInCents(2222);
    final Product added = mProductRepository.save(product);
    assertNotNull(added);
    assertEquals(product, added);
    Product found = mProductRepository.findByName(tName);
    assertNotNull(found);
  }*/


}
