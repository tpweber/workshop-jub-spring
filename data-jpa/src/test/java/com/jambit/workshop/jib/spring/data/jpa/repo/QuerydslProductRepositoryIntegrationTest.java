package com.jambit.workshop.jib.spring.data.jpa.repo;

import com.jambit.workshop.jib.spring.data.jpa.AbstractIntegrationTest;
import com.jambit.workshop.jib.spring.data.jpa.ApplicationConfig;
import com.jambit.workshop.jib.spring.data.jpa.domain.Product;
import com.jambit.workshop.jib.spring.data.jpa.domain.QProduct;
import com.mysema.query.types.Predicate;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Integration test for <code>Querydsl</code>
 */
@ContextConfiguration(classes = ApplicationConfig.class)
public class QuerydslProductRepositoryIntegrationTest extends AbstractIntegrationTest {

    static final QProduct product = QProduct.product;

    @Autowired
    ProductRepository repository;

    @Test
    public void testFindProductsByQuerydslPredicate() {

        Product iPad = repository.findOne(product.name.eq("iPad"));
        Predicate tablets = product.description.contains("tablet");

        Iterable<Product> result = repository.findAll(tablets);
        assertThat(result, is(Matchers.<Product>iterableWithSize(1)));
        assertThat(result, hasItem(iPad));
    }
}
