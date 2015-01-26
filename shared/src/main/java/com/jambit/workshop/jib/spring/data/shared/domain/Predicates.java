package com.jambit.workshop.jib.spring.data.shared.domain;

import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Thorsten on 05.01.2015.
 */
public class Predicates {

  public static Predicate<Long> isValidId() {
    return p -> Objects.nonNull(p) && p > 0L;
  }
  
  public static Predicate<String> isValidStringId() {
    return p ->StringUtils.isNotEmpty(p);
  }

  private Predicates() {
    // nop
  }
}
