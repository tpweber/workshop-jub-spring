package com.jambit.workshop.jib.spring.data.jdbc.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Product;
import com.jambit.workshop.jib.spring.data.jdbc.rest.ProductController;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;
import com.jambit.workshop.jib.spring.data.shared.exception.InvalidParameterException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/testApplicationContext.xml" })
@WebAppConfiguration
public class ProductControllerTest extends AbstractJUnit4SpringContextTests {

  @Autowired
  @Qualifier("productController")
  ProductController mController;

  @Test
  public void testGetExistingProduct() {
    Product product1 = mController.getProduct(1L);
    assertNotNull(product1);
    assertTrue(product1.hasValidId());
    assertEquals(Long.valueOf(1L), product1.getOptionalId().get());
    assertEquals("test1_name", product1.getName());
    assertEquals("description for test1_name", product1.getDescription());
    assertEquals(Integer.valueOf(256), product1.getPriceInCents());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testGetNotExistingProduct() {
    mController.getProduct(Long.MAX_VALUE);
  }

  @Test(expected = InvalidParameterException.class)
  public void testGetProductWithInvalidId() {
    mController.getProduct(-1L);
  }

}
