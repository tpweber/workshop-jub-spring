package com.jambit.workshop.jib.spring.data.jpa.repo;

import com.jambit.workshop.jib.spring.data.jpa.domain.Order;
import com.jambit.workshop.jib.spring.data.jpa.domain.OrderItem;
import com.jambit.workshop.jib.spring.data.jpa.domain.Product;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;

/**
 * Matchers for <code>Order</code> etc.
 */
public class OrderMatchers {

    public static <T> Matcher<Iterable<? super T>> containsOrder(Matcher<? super T> matcher) {
        return hasItem(matcher);
    }

    public static Matcher<Order> OrderItem(Matcher<OrderItem> matcher) {
        return hasProperty("orderItems", hasItem(matcher));
    }

    public static Matcher<OrderItem> Product(Matcher<Product> matcher) {
        return hasProperty("product", matcher);
    }
}
