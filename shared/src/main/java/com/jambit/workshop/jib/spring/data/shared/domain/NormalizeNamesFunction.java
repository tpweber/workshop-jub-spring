package com.jambit.workshop.jib.spring.data.shared.domain;

import java.util.function.BiFunction;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Function to normalize 2 Strings.
 */
public class NormalizeNamesFunction implements BiFunction<String, String, String> {
  @Override
  public String apply(final String first, final String second) {
    final String nFirst = StringEscapeUtils.escapeEcmaScript(first);
    final String nSecond = StringEscapeUtils.escapeEcmaScript(second);
    return createNormalizedString(nFirst, nSecond);
  }

  private static String createNormalizedString(final String nFirst, final String nSecond) {
    if(StringUtils.isNotEmpty(nFirst)) {
      if(StringUtils.isNotEmpty(nSecond)) {
        return nFirst.concat("_").concat(nSecond);
      }
      return nFirst;
    }
    return (StringUtils.isNotEmpty(nSecond)) ? nSecond : "";
  }
}
