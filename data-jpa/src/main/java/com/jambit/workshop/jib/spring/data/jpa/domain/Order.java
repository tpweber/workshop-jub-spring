package com.jambit.workshop.jib.spring.data.jpa.domain;

import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Order.
 */
@Entity
@Table(name = "Orders")
public class Order extends AbstractEntity {

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToOne
    private Address billingAddress;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Address shippingAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ORDER_ID")
    private Set<OrderItem> orderItems = new HashSet<OrderItem>();

    public Order(Customer customer, Address shippingAddress) {
        this(customer, shippingAddress, null);
    }


    public Order(Customer customer, Address shippingAddress, Address billingAddress) {

        Assert.notNull(customer);
        Assert.notNull(shippingAddress);

        this.customer = customer;
        this.shippingAddress = shippingAddress.getCopy();
        this.billingAddress = billingAddress == null ? null : billingAddress.getCopy();
    }

    protected Order() {

    }

    public void add(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Address getBillingAddress() {
        return billingAddress != null ? billingAddress : shippingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public Set<OrderItem> getOrderItems() {
        return Collections.unmodifiableSet(orderItems);
    }

    public BigDecimal getTotal() {

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : orderItems) {
            total = total.add(item.getTotal());
        }

        return total;
    }
}
