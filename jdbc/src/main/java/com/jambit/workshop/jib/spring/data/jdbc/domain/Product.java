package com.jambit.workshop.jib.spring.data.jdbc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Represents a product.
 */
public class Product extends AbstractEntity {

  private String mName;
  private String mDescription;
  private Integer mPriceInCents;

  public Product() {
    super();
  }

  public Product(long id) {
    super(id);
  }

  public Product withName(final String name) {
    mName = name;
    return this;
  }

  public Product withDescription(final String description) {
    mDescription = description;
    return this;
  }

  public Product withPriceInCents(final Integer priceInCents) {
    mPriceInCents = priceInCents;
    return this;
  }

  public String getName() {
    return mName;
  }

  public String getDescription() {
    return mDescription;
  }

  public Integer getPriceInCents() {
    return mPriceInCents;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (null == other || !(other instanceof Product)) {
      return false;
    }
    final Product that = (Product) other;
    return new EqualsBuilder().appendSuper(super.equals(that)).
        append(mName, that.mName).append(mDescription, that.mDescription).
        append(mPriceInCents, that.mPriceInCents).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().appendSuper(super.hashCode()).
        append(mName).append(mDescription).append(mPriceInCents).toHashCode();
  }

}
