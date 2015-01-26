package com.jambit.workshop.jib.spring.data.shared.util;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Thorsten on 06.01.2015.
 */
public final class OptionalMixin {
  private OptionalMixin(){
    // nop
  }
  @JsonProperty
  private Object value;
}
