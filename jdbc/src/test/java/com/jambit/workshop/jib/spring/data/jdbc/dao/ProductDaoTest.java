package com.jambit.workshop.jib.spring.data.jdbc.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jambit.workshop.jib.spring.data.jdbc.dao.ProductDao;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Product;

/**
 * Tests for <code>ConsumerDao</code>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/testApplicationContext.xml" })
public class ProductDaoTest extends AbstractJUnit4SpringContextTests {

  @Autowired
  ProductDao productDao;

  @Test
  public void testApplicationContext() throws Exception {
    assertNotNull(applicationContext);
    assertNotNull(productDao);
  }

  @Test
  public void testAddProduct() {
    Product product = new Product();
    product.withName("addProduct_name".concat(Instant.now().toString()));
    product.withDescription("addProduct_desc");
    product.withPriceInCents(100);
    assertFalse(product.getOptionalId().isPresent());
    final Product addedProduct = productDao.addProduct(product);
    assertNotNull(addedProduct);
    assertTrue(addedProduct.getOptionalId().isPresent());
    assertEquals(product.getName(), addedProduct.getName());
    assertEquals(product.getDescription(), addedProduct.getDescription());
    assertEquals(product.getPriceInCents(), addedProduct.getPriceInCents());
  }

  @Test
  public void testSelectProductById() {
    Product product = new Product();
    product.withName("selectProductById_name".concat(Instant.now().toString()));
    product.withDescription("selectProductById_desc");
    product.withPriceInCents(100);
    assertFalse(product.getOptionalId().isPresent());
    final Product addedProduct = productDao.addProduct(product);
    assertNotNull(addedProduct);
    assertTrue(addedProduct.getOptionalId().isPresent());
    final Long id = addedProduct.getOptionalId().get();
    assertTrue(id.intValue() > 0);
    final Product selectedProduct = productDao.selectProduct(id);
    assertNotNull(selectedProduct);
    assertEquals(addedProduct, selectedProduct);
  }
}
