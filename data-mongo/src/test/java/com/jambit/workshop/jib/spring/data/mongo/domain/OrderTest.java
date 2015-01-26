package com.jambit.workshop.jib.spring.data.mongo.domain;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTest {

    @Test
    public void testCreation() {
        Order order = new Order("orderId");
        assertTrue(order.hasValidId());
        assertNotNull(order.getOrderItems());
        assertTrue(order.getOrderItems().isEmpty());
        assertEquals(OrderStatus.NEW, order.getOrderStatus());
        assertNull(order.getCustomer());
        assertNotNull(order.getShippingAddress());
        assertFalse(order.getShippingAddress().isPresent());
        assertNull(order.getBillingAddress());
    }

    @Test
    public void testCustomer() {
        Customer mockedCustomer = mock(Customer.class);
        when(mockedCustomer.getOptionalId()).thenReturn(Optional.ofNullable("customerId"));
        Order order = new Order("orderId");
        assertEquals(order, order.withCustomer(mockedCustomer));
        assertEquals(mockedCustomer, order.getCustomer());
        assertEquals(Optional.ofNullable("customerId"), order.getCustomer().getOptionalId());
        when(mockedCustomer.getOptionalId()).thenReturn(Optional.ofNullable(null));
        assertEquals(order, order.withCustomer(mockedCustomer));
        assertEquals(Optional.empty(), order.getCustomer().getOptionalId());
    }

    @Test
    public void testAddresses() {
        Address mockedShipping = mock(Address.class);
        when(mockedShipping.getOptionalId()).thenReturn(Optional.ofNullable("shipping"));
        Address mockedBilling = mock(Address.class);
        when(mockedBilling.getOptionalId()).thenReturn(Optional.ofNullable("billing"));
        Order order = new Order("orderId");
        assertEquals(order, order.withAddresses(mockedBilling, mockedShipping));
        assertEquals(Optional.of(mockedShipping), order.getShippingAddress());
        assertEquals(mockedBilling, order.getBillingAddress());
    }

    @Test
    public void testOrderItems() {
        OrderItem mockedItem1 = mock(OrderItem.class);
        when(mockedItem1.getOptionalId()).thenReturn(Optional.of("item1"));
        OrderItem mockedItem2 = mock(OrderItem.class);
        when(mockedItem2.getOptionalId()).thenReturn(Optional.of("item2"));
        Order order = new Order("orderId");
        assertEquals(order, order.withOrderItems(mockedItem1, mockedItem2));
        List<OrderItem> orderItems = order.getOrderItems();
        assertEquals(2, orderItems.size());
        assertEquals(mockedItem1, orderItems.get(0));
        assertEquals(mockedItem2, orderItems.get(1));
    }


}
