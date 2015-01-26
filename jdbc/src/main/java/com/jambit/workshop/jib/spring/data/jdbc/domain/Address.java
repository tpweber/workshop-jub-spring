package com.jambit.workshop.jib.spring.data.jdbc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.neovisionaries.i18n.CountryCode;

/**
 * Represents the address of a customer in context of an order.
 */
public class Address extends AbstractEntity {

  private String mStreet;
  private String mCity;
  private CountryCode mCountry;

  public Address() {
    super();
  }

  public Address(Long id) {
    super(id);
  }

  public Address with(final String street, final String city, final CountryCode country) {
    mStreet = street;
    mCity = city;
    mCountry = country;
    return this;
  }

  public String getStreet() {
    return mStreet;
  }

  public String getCity() {
    return mCity;
  }

  public CountryCode getCountry() {
    return mCountry;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (null == other || !(other instanceof Address)) {
      return false;
    }
    final Address that = (Address) other;
    return new EqualsBuilder().appendSuper(super.equals(that)).
        append(mStreet, that.mStreet).append(mCity, that.mCity).
        append(mCountry, that.mCountry).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().appendSuper(super.hashCode()).
        append(mStreet).append(mCity).append(mCountry).toHashCode();
  }

}
