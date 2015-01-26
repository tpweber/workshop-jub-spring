package com.jambit.workshop.jib.spring.data.jpa.repo;

import com.jambit.workshop.jib.spring.data.jpa.AbstractIntegrationTest;
import com.jambit.workshop.jib.spring.data.jpa.ApplicationConfig;
import com.jambit.workshop.jib.spring.data.jpa.domain.Customer;
import com.jambit.workshop.jib.spring.data.jpa.domain.EmailAddress;
import com.jambit.workshop.jib.spring.data.jpa.domain.Order;
import com.jambit.workshop.jib.spring.data.jpa.domain.OrderItem;
import com.jambit.workshop.jib.spring.data.jpa.domain.Product;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static com.jambit.workshop.jib.spring.data.jpa.repo.ProductMatchers.named;
import static com.jambit.workshop.jib.spring.data.jpa.repo.ProductMatchers.with;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for <code>OrderRepository</code>
 */
@ContextConfiguration(classes = ApplicationConfig.class)
public class OrderRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    OrderRepository repository;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testCreateOrder() {

        Customer dave = customerRepository.findByEmailAddress(new EmailAddress("dave@depechemode.com"));
        Product iPad = productRepository.findOne(1L);

        Order order = new Order(dave, dave.getAddresses().iterator().next());
        order.add(new OrderItem(iPad));

        order = repository.save(order);
        assertThat(order.getId(), is(notNullValue()));
    }

    @Test
    public void testFindByCustomer() {

        Customer dave = customerRepository.findByEmailAddress(new EmailAddress("dave@depechemode.com"));
        List<Order> orders = repository.findByCustomer(dave);
        Matcher<Iterable<? super Order>> hasOrderForiPad = OrderMatchers.containsOrder(with(OrderMatchers.OrderItem((with(OrderMatchers.Product(named("iPad")))))));

        assertThat(orders, hasSize(1));
        assertThat(orders, hasOrderForiPad);
    }
}
