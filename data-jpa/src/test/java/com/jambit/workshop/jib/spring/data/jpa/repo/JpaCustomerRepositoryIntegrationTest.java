package com.jambit.workshop.jib.spring.data.jpa.repo;

import com.jambit.workshop.jib.spring.data.jpa.AbstractIntegrationTest;
import com.jambit.workshop.jib.spring.data.jpa.PlainJpaConfig;
import com.jambit.workshop.jib.spring.data.jpa.domain.Customer;
import com.jambit.workshop.jib.spring.data.jpa.domain.EmailAddress;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Integration test for the <code>JpaCustomerRepository</code>
 */
@ContextConfiguration(classes = PlainJpaConfig.class)
public class JpaCustomerRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    CustomerRepository repository;

    @Test(expected = PersistenceException.class)
    public void insertsInvalidCustomer() {
        Customer customer = new Customer("Alicia", "Keys");
        customer = repository.save(customer);
    }

    @Test
    public void insertsNewCustomerCorrectly() {
        final String sEmail = "ak_".concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))).concat("@test.de");
        final EmailAddress email = new EmailAddress(sEmail);
        Customer customer = new Customer("Alicia", "Keys");
        customer.setEmailAddress(email);
        customer = repository.save(customer);

        assertThat(customer.getId(), is(notNullValue()));
        assertEquals("Alicia", customer.getFirstname());
    }

    @Test
    public void updatesCustomerCorrectly() {

        final Customer andrew = repository.findByEmailAddress(new EmailAddress("andrew@depechemode.com"));
        assertThat(andrew, is(notNullValue()));

        andrew.setLastname("Miller");
        final Customer savedAndrew = repository.save(andrew);

        final Customer foundAndrew = repository.findByEmailAddress(savedAndrew.getEmailAddress());
        assertThat(foundAndrew.getLastname(), is(savedAndrew.getLastname()));
        assertThat(foundAndrew.getId(), is(savedAndrew.getId()));
    }
}
