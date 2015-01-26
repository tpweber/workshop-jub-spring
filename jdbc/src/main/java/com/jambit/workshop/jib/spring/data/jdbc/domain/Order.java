package com.jambit.workshop.jib.spring.data.jdbc.domain;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by thweber on 30.12.14.
 */
public class Order extends AbstractEntity {

  private Customer mCustomer;
  private Optional<Address> mShippingAddress;
  private Address mBillingAddress;
  private final OrderStatus mStatus;
  private LocalDateTime mOrderDate;

  public Order() {
    mStatus = OrderStatus.NEW;
  }

  public Order(long id, final OrderStatus status) {
    super(id);
    mStatus = status;
  }

  public Customer getCustomer() {
    return mCustomer;
  }

  public Optional<Address> getShippingAddress() {
    return mShippingAddress;
  }

  public Address getBillingAddress() {
    return mBillingAddress;
  }

  public OrderStatus getStatus() {
    return mStatus;
  }

  public LocalDateTime getOrderDate() {
    return mOrderDate;
  }

}
