package com.jambit.workshop.jib.spring.data.jdbc.manager;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Product;

/**
 * API for Product-related things.
 */
public interface ProductManager {
  Product getProduct(long id);
}
