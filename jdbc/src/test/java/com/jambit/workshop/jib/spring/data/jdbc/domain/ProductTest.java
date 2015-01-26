package com.jambit.workshop.jib.spring.data.jdbc.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Product;

/**
 * Tests for <code>Product</code>
 */
public class ProductTest {
  @Test
  public void testCreation() {
    Product product = new Product();
    assertNotNull(product.getOptionalId());
    assertFalse(product.getOptionalId().isPresent());
    assertNull(product.getDescription());
    assertNull(product.getName());
    assertNull(product.getPriceInCents());
  }

  @Test
  public void testName() {
    Product product = new Product();
    product.withName("testName");
    assertEquals("testName", product.getName());
    product.withName("");
    assertEquals("", product.getName());
    product.withName(null);
    assertNull(product.getName());
  }

  @Test
  public void testDescription() {
    Product product = new Product();
    product.withDescription("testDescription");
    assertEquals("testDescription", product.getDescription());
    product.withDescription("");
    assertEquals("", product.getDescription());
    product.withDescription(null);
    assertNull(product.getDescription());
  }

  @Test
  public void testPriceInCents() {
    Product product = new Product();
    product.withPriceInCents(99);
    assertEquals(Integer.valueOf(99), product.getPriceInCents());
    product.withPriceInCents(0);
    assertEquals((Integer.valueOf(0)), product.getPriceInCents());
    product.withPriceInCents(null);
    assertNull(product.getPriceInCents());
  }

  @Test
  public void testEqualsHashCode() {
    Product product1 = new Product();
    product1.withDescription("testDescription");
    product1.withName("testName");
    product1.withPriceInCents(99);
    Product product2 = new Product();
    product2.withDescription("testDescription");
    product2.withName("testName");
    product2.withPriceInCents(99);
    assertEquals(product1, product2);
    assertEquals(product1.hashCode(), product2.hashCode());
    product2.withDescription("testDescription2");
    assertNotEquals(product1, product2);
    assertNotEquals(product1.hashCode(), product2.hashCode());
  }
}
