package com.jambit.workshop.jib.spring.data.jdbc.domain;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by Thorsten on 05.01.2015.
 */
public class IdCheck implements Predicate<Long> {
  @Override
  public boolean test(final Long aLong) {
    return Objects.nonNull(aLong) && aLong > 0L;
  }
}
