package com.jambit.workshop.jib.spring.data.jdbc.domain;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Returns the id aka PK as <code>java.util.Optional</code>.
 */
public class AbstractEntity {
  private final Long mId;

  protected AbstractEntity() {
    mId = null;
  }

  protected AbstractEntity(final Long id) {
    mId = id;
  }

  public Optional<Long> getOptionalId() {
    return Optional.ofNullable(mId);
  }

  public boolean hasValidId() {
    return getOptionalId().isPresent();
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (null == other || !(other instanceof AbstractEntity)) {
      return false;
    }
    final AbstractEntity that = (AbstractEntity) other;
    return new EqualsBuilder().append(mId, that.mId).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(mId).toHashCode();
  }
}

