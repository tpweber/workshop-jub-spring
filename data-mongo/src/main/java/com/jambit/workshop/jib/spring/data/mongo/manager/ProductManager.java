package com.jambit.workshop.jib.spring.data.mongo.manager;

import java.util.List;

import com.jambit.workshop.jib.spring.data.mongo.domain.Product;

public interface ProductManager {
  Product addProduct(Product product);
  
  Product getProduct(String productId);
  
  List<Product> getAllProductsSortedByName(String direction);

  List<Product> findByName(String pName);
}
