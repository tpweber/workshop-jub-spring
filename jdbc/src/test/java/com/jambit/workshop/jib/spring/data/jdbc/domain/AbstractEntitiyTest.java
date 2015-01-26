package com.jambit.workshop.jib.spring.data.jdbc.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import com.jambit.workshop.jib.spring.data.jdbc.domain.AbstractEntity;

/**
 * Tests for <code>AbstractEntitiy</code>
 */
public class AbstractEntitiyTest {
  @Test
  public void testCreation() {
    AbstractEntity entity1 = new AbstractEntity();
    assertNotNull(entity1.getOptionalId());
    assertFalse(entity1.hasValidId());
    assertEquals(new AbstractEntity(), entity1);
    assertEquals(new AbstractEntity().hashCode(), entity1.hashCode());
  }

  @Test
  public void testId() {
    AbstractEntity entity1 = new AbstractEntity();
    assertNotNull(entity1.getOptionalId());
    assertFalse(entity1.hasValidId());
    AbstractEntity entity2 = new AbstractEntity(1L);
    Optional<Long> oId2 = entity2.getOptionalId();
    assertNotNull(oId2);
    assertTrue(oId2.isPresent());
    assertEquals(Long.valueOf(1L), oId2.get());
  }
}
