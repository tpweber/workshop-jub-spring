package com.jambit.workshop.jib.spring.data.mongo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jambit.workshop.jib.spring.data.mongo.json.ProductJsonDeserializer;
import com.jambit.workshop.jib.spring.data.mongo.json.ProductJsonSerializer;
import org.mongodb.morphia.annotations.Entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Represents a product.
 */
@Entity
@Document(collection = "products")
//@JsonSerialize(using = ProductJsonSerializer.class)
//@JsonDeserialize(using = ProductJsonDeserializer.class)
public class Product extends AbstractDocument {

  @TextIndexed
  @Field("name")
  @JsonProperty("name")
  private String mName;
  @Field("description")
  @JsonProperty("description")
  private String mDescription;
  @Field("price")
  @JsonProperty("priceInCents")
  private Integer mPriceInCents;

  public Product() {
    this(null);
  }

  @PersistenceConstructor
  public Product(@Value("#root.id") final String pId) {
    super(pId);
  }

  public Product(final String pId, final String pName, final String pDescription, int pPriceInCents) {
    this(pId);
    withName(pName).withDescription(pDescription).withPriceInCents(pPriceInCents);
  }

  public Product withName(final String pName) {
    mName = pName;
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

  @JsonProperty("name")
  public final String getProductName() {
    return mName;
  }

  public String getDescription() {
    return mDescription;
  }

  @JsonProperty("priceInCents")
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
