package com.jambit.workshop.jib.spring.data.jdbc.domain;

/**
 * Represents the status of a given order.
 */
public enum OrderStatus {
  NEW(0), PENDING(1), PROCESSING(2), COMPLETE(3), CANCELED(4), HOLDED(5), CLOSED(6);

  private final int mStatusId;
  private OrderStatus(int statusId) {
    mStatusId = statusId;
  }

  public int getStatusId() {
    return mStatusId;
  }

  public boolean hasStatusId(int statusId) {
    return mStatusId == statusId;
  }

  public static OrderStatus getByStatusId(int id) {
    for (OrderStatus orderStatus : values()) {
      if(orderStatus.hasStatusId(id)) {
        return orderStatus;
      }
    }
    return NEW;
  }
}
