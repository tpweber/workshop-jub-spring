package com.jambit.workshop.jib.spring.data.jpa.repo;

import com.jambit.workshop.jib.spring.data.jpa.AbstractIntegrationTest;
import com.jambit.workshop.jib.spring.data.jpa.ApplicationConfig;
import com.jambit.workshop.jib.spring.data.jpa.domain.Address;
import com.jambit.workshop.jib.spring.data.jpa.domain.Customer;
import com.jambit.workshop.jib.spring.data.jpa.domain.EmailAddress;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for <code>CustomerRepository</code>.
 */
@ContextConfiguration(classes = ApplicationConfig.class)
public class CustomerRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    @Qualifier("customerRepository")
    CustomerRepository repository;

    @Test
    public void testSaveCustomerCorrectly() {

        EmailAddress email = new EmailAddress("alicia@keys.com");

        Customer dave = new Customer("Alicia", "Keys");
        dave.setEmailAddress(email);
        dave.add(new Address("27 Broadway", "New York", "United States"));

        Customer result = repository.save(dave);
        assertThat(result.getId(), is(notNullValue()));
    }

    @Test
    public void testFindCustomerByEmail() {

        EmailAddress email = new EmailAddress("alicia@keys.com");
        Customer alicia = new Customer("Alicia", "Keys");
        alicia.setEmailAddress(email);

        repository.save(alicia);

        Customer result = repository.findByEmailAddress(email);
        assertThat(result, is(alicia));
    }

    @Test
    public void preventsDuplicateEmail() {

        Customer martin = repository.findByEmailAddress(new EmailAddress("martin@depechemode.com"));

        Customer anotherMartin = new Customer("Dave", "Matthews");
        anotherMartin.setEmailAddress(martin.getEmailAddress());

        repository.save(anotherMartin);
    }
}
