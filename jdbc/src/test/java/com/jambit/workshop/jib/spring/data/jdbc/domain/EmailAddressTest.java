package com.jambit.workshop.jib.spring.data.jdbc.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jambit.workshop.jib.spring.data.jdbc.domain.EmailAddress;

/**
 * Tests for <code>EmailAddress</code>
 */
public class EmailAddressTest {
  @Test
  public void testValidEmailAddress() {
    assertTrue(EmailAddress.isValid("tpweber@web.de"));
    EmailAddress ea1 = new EmailAddress("tpweber@web.de");
    assertEquals("tpweber@web.de", ea1.getAsString());
    assertTrue(ea1.isValid());
  }

  @Test
  public void testInvalidEmailAddress() {
    assertFalse(EmailAddress.isValid("tpweber*web.de"));
    EmailAddress ea1 = new EmailAddress("tpweber*web.de");
    assertEquals("tpweber*web.de", ea1.getAsString());
    assertFalse(ea1.isValid());
  }

}
