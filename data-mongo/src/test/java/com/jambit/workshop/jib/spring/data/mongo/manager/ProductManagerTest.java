package com.jambit.workshop.jib.spring.data.mongo.manager;

import com.jambit.workshop.jib.spring.data.mongo.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/testApplicationContext.xml"})
public class ProductManagerTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    ProductManager mProductManager;

    @Test
    public void testContext() {
        assertNotNull(applicationContext);
        assertNotNull(mProductManager);
    }

    @Test
    public void testAddProduct() {
        String productId1 = createId();
        Product product2Add1 = new Product(productId1);
        product2Add1.withDescription("testAddProduct1_description").withName("testAddProduct1_name").withPriceInCents(1000);
        Product added = mProductManager.addProduct(product2Add1);
        assertNotNull(added);
        assertEquals(product2Add1, added);
        Product product2Add2 = new Product(null);
        product2Add2.withDescription("testAddProduct2_description").withName("testAddProduct2_name").withPriceInCents(2000);
        assertFalse(product2Add2.hasValidId());
        Product added2 = mProductManager.addProduct(product2Add2);
        assertNotNull(added2);
        assertTrue(added2.hasValidId());
    }

    @Test
    public void testGetProduct() {
        String productId = createId();
        Product product2Add = new Product(productId);
        product2Add.withDescription("testGetProduct_description").withName("testGetProduct_name").withPriceInCents(11111);
        Product added = mProductManager.addProduct(product2Add);
        assertNotNull(added);
        assertEquals(product2Add, added);
        Product found = mProductManager.getProduct(productId);
        assertNotNull(found);
        assertEquals(product2Add, found);
    }

    @Test
    public void testFindByName() {
        Product product2Add = new Product();
        String testName = "testFindByName_name";
        product2Add.withDescription("testFindByNamet_description").withName(testName).withPriceInCents(11111);
        Product added = mProductManager.addProduct(product2Add);
        assertNotNull(added);
        assertEquals(product2Add, added);
        List<Product> found = mProductManager.findByName(testName);
        assertNotNull(found);
        assertFalse(found.isEmpty());
        assertTrue(found.contains(added));
    }

    private static String createId() {
        return UUID.randomUUID().toString();
    }
}
