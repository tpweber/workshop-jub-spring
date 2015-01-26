package com.jambit.workshop.jib.spring.data.mongo.rest;

import com.jambit.workshop.jib.spring.data.mongo.domain.Product;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;
import com.jambit.workshop.jib.spring.data.shared.exception.InvalidParameterException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/testApplicationContext.xml"})
@WebAppConfiguration
public class ProductControllerTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    @Qualifier("productController")
    ProductController mController;

    @Test
    public void testGetExistingProduct() {
        Product productToAdd = new Product();
        productToAdd.withDescription("testGetExistingProduct_description").withName("testGetExistingProduct_name").withPriceInCents(1000);
        assertFalse(productToAdd.hasValidId());
        Product added = mController.addProduct(productToAdd);
        assertNotNull(added);
        assertTrue(added.hasValidId());
        assertEquals("testGetExistingProduct_name", added.getProductName());
        assertEquals("testGetExistingProduct_description", added.getDescription());
        assertEquals(Integer.valueOf(1000), added.getPriceInCents());
        Product found = mController.getProduct(added.getOptionalId().get());
        assertEquals(added, found);
    }

    @Test
    public void testFindAllProducts() {
        Product productToAdd = new Product();
        productToAdd.withDescription("testFindAllProducts_description").withName("testFindAllProducts_name").withPriceInCents(1000);
        assertFalse(productToAdd.hasValidId());
        Product added = mController.addProduct(productToAdd);
        assertNotNull(added);
        assertTrue(added.hasValidId());
        List<Product> allProducts = mController.findAllProducts("asc");
        assertNotNull(allProducts);
        assertFalse(allProducts.isEmpty());
        assertTrue(allProducts.contains(added));
    }

    @Test
    public void testFindProductByName() {
        final String name = "testFindProductByName_name".concat(Instant.now().toString());
        Product productToAdd = new Product();
        productToAdd.withDescription("testFindProductByName_description").withName(name).withPriceInCents(1000);
        assertFalse(productToAdd.hasValidId());
        Product added = mController.addProduct(productToAdd);
        assertNotNull(added);
        assertTrue(added.hasValidId());
        List<Product> allProducts = mController.findProductByName(name);
        assertNotNull(allProducts);
        assertFalse(allProducts.isEmpty());
        assertTrue(allProducts.contains(added));
    }

    @Test
    public void testFindProductByNameNotExisting() {
        final String name = "kvmlvvafkvnoöijgoirejdklskcäöldskcl".concat(Instant.now().toString());
        List<Product> allProducts = mController.findProductByName(name);
        assertNotNull(allProducts);
        assertTrue(allProducts.isEmpty());
    }


    @Test(expected = EntityNotFoundException.class)
    public void testGetNotExistingProduct() {
        mController.getProduct(Long.toString(Long.MAX_VALUE));
    }

    @Test(expected = InvalidParameterException.class)
    public void testGetProductWithInvalidId() {
        mController.getProduct("");
    }

}
