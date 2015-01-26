package com.jambit.workshop.jib.spring.data.jdbc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.jambit.workshop.jib.spring.data.shared.domain.NormalizeNamesFunction;

/**
 * Represents the customer.
 */
public class Customer extends AbstractEntity {
  private String mFirstName;
  private String mLastName;
  private String mNormalizedName;
  private EmailAddress mEmailAddress;
  private final NormalizeNamesFunction mNormalizeFunction;

  public Customer() {
    mNormalizeFunction = new NormalizeNamesFunction();
  }

  public Customer(long id) {
    super(id);
    mNormalizeFunction = new NormalizeNamesFunction();
  }

  public Customer withEmailAddress(final EmailAddress emailAddress) {
    mEmailAddress = emailAddress;
    return this;
  }

  public Customer withName(final String firstName, final String lastName) {
    mFirstName = firstName;
    mLastName = lastName;
    mNormalizedName = normalizeName();
    return this;
  }

  private String normalizeName() {
    return mNormalizeFunction.apply(mLastName, mFirstName);
  }

  public String getFirstName() {
    return mFirstName;
  }

  public String getLastName() {
    return mLastName;
  }

  public String getNormalizedName() {
    return mNormalizedName;
  }

  public EmailAddress getEmailAddress() {
    return mEmailAddress;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (null == other || !(other instanceof Customer)) {
      return false;
    }
    final Customer that = (Customer) other;
    return new EqualsBuilder().appendSuper(super.equals(that)).
        append(mFirstName, that.mFirstName).append(mLastName, that.mLastName).
        append(mEmailAddress, that.mEmailAddress).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().appendSuper(super.hashCode()).
        append(mFirstName).append(mLastName).append(mEmailAddress).toHashCode();
  }
}

