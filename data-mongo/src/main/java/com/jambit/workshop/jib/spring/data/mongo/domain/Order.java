package com.jambit.workshop.jib.spring.data.mongo.domain;

import java.util.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.util.CollectionUtils;

@Document(collection = "orders")
public class Order extends AbstractDocument  {
  
  @Field("items")
  private final List<OrderItem> mOrderItems;
  @DBRef
  private Customer customer;
  @DBRef
  private Address shippingAddress;
  @DBRef
  private Address billingAddress;
  @Field("status")
  private OrderStatus mStatus;

  public Order() {
    this(null);
  }

  public Order(@Value("#root.id") final String pId) {
    this(pId, OrderStatus.NEW);
  }

  public Order(@Value("#root.id") final String pId, @Value("#root.status") final OrderStatus status) {
    super(pId);
    mOrderItems = new ArrayList<>();
    mStatus = status;
  }

  public List<OrderItem> getOrderItems() {
    return new ArrayList<>(mOrderItems);
  }

  public OrderStatus getOrderStatus() {
    return mStatus;
  }

  public Customer getCustomer() {
    return customer;
  }

  public Optional<Address> getShippingAddress() {
    return Optional.ofNullable(shippingAddress);
  }

  public Address getBillingAddress() {
    return billingAddress;
  }

  public Order withCustomer(final Customer customer) {
    this.customer = customer;
    return this;
  }

  public Order withAddresses(final Address billing, final Address shipping) {
    billingAddress = billing;
    shippingAddress = shipping;
    return this;
  }

  public Order withOrderItems(final OrderItem... orderItems) {
    if(null != orderItems) {
      mOrderItems.clear();
      mOrderItems.addAll(Arrays.asList(orderItems));
    }
    return this;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (null == other || !(other instanceof Order)) {
      return false;
    }
    final Order that = (Order) other;
    return new EqualsBuilder().appendSuper(super.equals(that)).
            append(mOrderItems, that.mOrderItems).append(customer, that.customer).
            append(billingAddress, that.billingAddress).append(shippingAddress, that.shippingAddress).
            append(mStatus, that.mStatus).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().appendSuper(super.hashCode()).
            append(mOrderItems).append(customer).append(billingAddress).append(shippingAddress).
            append(mStatus).toHashCode();
  }
}
