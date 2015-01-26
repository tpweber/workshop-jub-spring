package com.jambit.workshop.jib.spring.data.jpa.repo;

import com.jambit.workshop.jib.spring.data.jpa.AbstractIntegrationTest;
import com.jambit.workshop.jib.spring.data.jpa.domain.Product;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Integration tests for <code>ProductRepository</code>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/application-context.xml"})
public class ProductRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ProductRepository repository;

    @Test
    public void createProduct() {
        Product product = new Product("Camera bag", new BigDecimal(49.99));
        assertNotNull(repository.save(product));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void lookupProductsByDescription() {
        final Pageable pageable = new PageRequest(0, 1, Direction.DESC, "name");
        final Page<Product> page = repository.findByDescriptionContaining("Apple", pageable);
        assertThat(page.getContent(), hasSize(1));
        assertThat(page, Matchers.<Product>hasItems(ProductMatchers.named("iPad")));
        assertThat(page.getTotalElements(), is(2L));
        assertThat(page.isFirst(), is(true));
        assertThat(page.isLast(), is(false));
        assertThat(page.hasNext(), is(true));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFindByAttributeAndValue() {
        final List<Product> products = repository.findByAttributeAndValue("connector", "plug");
        assertThat(products, Matchers.<Product>hasItems(ProductMatchers.named("Dock")));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFindByPriceRange() {
        final List<Product> products1 = repository.findByPriceRange(new BigDecimal(0), new BigDecimal(100));
        assertEquals(1, products1.size());
        assertThat(products1, Matchers.<Product>hasItems(ProductMatchers.named("Dock")));
        final List<Product> products2 = repository.findByPriceRange(new BigDecimal(400), new BigDecimal(2000));
        assertEquals(2, products2.size());
        assertThat(products2, Matchers.<Product>hasItems(ProductMatchers.named("iPad")));
        assertThat(products2, Matchers.<Product>hasItems(ProductMatchers.named("MacBook Pro")));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFindFromPrice() {
        final List<Product> products1 = repository.findFromPrice(new BigDecimal(0));
        assertEquals(3, products1.size());
        assertThat(products1, Matchers.<Product>hasItems(ProductMatchers.named("Dock")));
        assertThat(products1, Matchers.<Product>hasItems(ProductMatchers.named("iPad")));
        assertThat(products1, Matchers.<Product>hasItems(ProductMatchers.named("MacBook Pro")));
        final List<Product> products2 = repository.findFromPrice(new BigDecimal(400));
        assertEquals(2, products2.size());
        assertThat(products2, Matchers.<Product>hasItems(ProductMatchers.named("iPad")));
        assertThat(products2, Matchers.<Product>hasItems(ProductMatchers.named("MacBook Pro")));
    }
}
