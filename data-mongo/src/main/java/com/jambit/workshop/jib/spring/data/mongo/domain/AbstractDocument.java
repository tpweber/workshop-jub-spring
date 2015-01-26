package com.jambit.workshop.jib.spring.data.mongo.domain;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;

public class AbstractDocument {
  @Id
  private final String mId;
  
  protected AbstractDocument(final String pId) {
    mId = pId;
  }

  @JsonProperty("optionalId")
  public Optional<String> getOptionalId() {
    return Optional.ofNullable(mId);
  }

  @JsonProperty("id")
  public String getId() {
    return mId;
  }
  
  public boolean hasValidId() {
    return getOptionalId().isPresent();
  }
  
  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (null == other || !(other instanceof AbstractDocument)) {
      return false;
    }
    final AbstractDocument that = (AbstractDocument) other;
    return new EqualsBuilder().append(mId, that.mId).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(mId).toHashCode();
  }
}
