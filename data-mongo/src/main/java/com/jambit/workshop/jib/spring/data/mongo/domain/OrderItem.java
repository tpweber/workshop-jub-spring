package com.jambit.workshop.jib.spring.data.mongo.domain;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

public class OrderItem extends AbstractDocument  {
  
  @DBRef
  private Product product;
  @Field("count")
  private int mCount;
  @Field("total")
  private Integer mPriceInCents;

  public OrderItem(final Product product, int count) {
    this(null, product, count);
  }

  public OrderItem(@Value("#root.id") final String pId, @Value("#root.product") final Product product, @Value("#root.count") int count) {
    this(pId, product, count, calcTotalPrice(product, count));
  }

  @PersistenceConstructor
  public OrderItem(@Value("#root.id") final String pId, @Value("#root.product") final Product product, @Value("#root.count") int count,  @Value("#root.total") int price) {
    super(pId);
    this.product = product;
    mCount = count;
    mPriceInCents = calcTotalPrice(product, count);    
  }
  
  private static final int calcTotalPrice(final Product product, int count) {
    if(!Objects.isNull(product) && count > 0) {
      return product.getPriceInCents() * count;
    }
    return 0;
  }
  
  public OrderItem withSpecialPrice(int priceInCents) {
    mPriceInCents = priceInCents;
    return this;
  }
  
  public Product getProduct() {
    return product;
  }
  
  public int getCount() {
    return mCount;
  }
  
  public int getTotalPriceInCents() {
    return mPriceInCents;
  }
  
  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (null == other || !(other instanceof OrderItem)) {
      return false;
    }
    final OrderItem that = (OrderItem) other;
    return new EqualsBuilder().appendSuper(super.equals(that)).
        append(product, that.product).append(mCount, that.mCount).
        append(mPriceInCents, that.mPriceInCents).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().appendSuper(super.hashCode()).
        append(product).append(mCount).append(mPriceInCents).toHashCode();
  }
}
