package com.jambit.workshop.jib.spring.data.jdbc.dao;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Order;

/**
 * DAO for <code>Order</code>
 */
public interface OrderDao {
  Order addOrder(Order order);
}
