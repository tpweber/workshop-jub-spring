package com.jambit.workshop.jib.spring.data.shared.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class NormalizeNamesFunctionTest {

  @Test
  public void testWithoutSpecialSigns() {
    NormalizeNamesFunction nnf = new NormalizeNamesFunction();
    String applied = nnf.apply("first", "second");
    assertNotNull(applied);
    assertEquals("first_second", applied);
  }
  
  @Test
  public void testNulls() {
    NormalizeNamesFunction nnf_first = new NormalizeNamesFunction();
    String applied_first = nnf_first.apply("first", null);
    assertNotNull(applied_first);
    assertEquals("first", applied_first);
    NormalizeNamesFunction nnf_second = new NormalizeNamesFunction();
    String applied_second = nnf_second .apply(null, "second");
    assertNotNull(applied_second);
    assertEquals("second", applied_second);
    NormalizeNamesFunction nnf= new NormalizeNamesFunction();
    String applied= nnf.apply(null, null);
    assertNotNull(applied);
    assertEquals("", applied);
  }
  
}
