package com.jambit.workshop.jib.spring.data.jpa;

import com.jambit.workshop.jib.spring.data.jpa.repo.CustomerRepository;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Test for validating configuration.
 */
public class ApplicationConfigTest {

    @Test
    public void bootstrapAppFromJavaConfig() {
        final ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        assertThat(context, is(notNullValue()));
        assertThat(context.getBean(CustomerRepository.class), is(notNullValue()));
    }

    @Test
    public void bootstrapAppFromXml() {
        final ApplicationContext context = new ClassPathXmlApplicationContext("spring/application-context.xml");
        assertThat(context, is(notNullValue()));
        assertThat(context.getBean(CustomerRepository.class), is(notNullValue()));
    }
}
