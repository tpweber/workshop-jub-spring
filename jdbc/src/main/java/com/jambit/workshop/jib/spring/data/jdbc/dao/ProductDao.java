package com.jambit.workshop.jib.spring.data.jdbc.dao;

import com.jambit.workshop.jib.spring.data.jdbc.domain.Product;

/**
 * DAO for <code>Product</code>
 */
public interface ProductDao {
  Product addProduct(Product product);

  Product selectProduct(long id);
}
