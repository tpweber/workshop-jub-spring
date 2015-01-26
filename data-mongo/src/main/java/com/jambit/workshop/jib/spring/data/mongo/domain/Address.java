package com.jambit.workshop.jib.spring.data.mongo.domain;

import com.neovisionaries.i18n.CountryCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "addresses")
public class Address extends AbstractDocument  {

  @TextIndexed
  @Field("street")
  private String mStreet;
  @Indexed(direction = IndexDirection.ASCENDING)
  @Field("city")
  private String mCity;
  @Indexed(direction = IndexDirection.ASCENDING)
  @Field("country")
  private CountryCode mCountry;
  @DBRef
  private Customer mCustomer;

  protected Address(@Value("#root.id") final String pId) {
    super(pId);
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

  public Customer getCustomer() {
    return mCustomer;
  }

  public Address withCustomer(final Customer customer) {
    mCustomer = customer;
    return this;
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
