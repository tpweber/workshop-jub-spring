package com.jambit.workshop.jib.spring.data.mongo.manager.impl;

import static com.jambit.workshop.jib.spring.data.shared.domain.Predicates.isValidStringId;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jambit.workshop.jib.spring.data.mongo.domain.Product;
import com.jambit.workshop.jib.spring.data.mongo.manager.ProductManager;
import com.jambit.workshop.jib.spring.data.mongo.repo.ProductRepository;
import com.jambit.workshop.jib.spring.data.mongo.repo.Sorts;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;
import com.jambit.workshop.jib.spring.data.shared.exception.InvalidParameterException;
import org.springframework.util.CollectionUtils;

@Service("productManager")
public class ProductManagerImpl implements ProductManager {

  @Autowired
  @Qualifier("productRepository")
  ProductRepository mProductRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  @Override
  public Product addProduct(final Product product) {
    if (isValidProduct(product)) {
      return mProductRepository.save(product);
    }
    throw new InvalidParameterException("Product is invalid.");
  }

  private static boolean isValidProduct(final Product product) {
    return null != product;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  @Override
  public Product getProduct(String productId) {
    if (isValidId(productId)) {
      final Product found = mProductRepository.findOne(productId);
      if (isValidProduct(found)) {
        return found;
      }
      throw new EntityNotFoundException("No product found for id[" + productId + "].");
    }
    return null;
  }

  private static boolean isValidId(final String productId) {
    return isValidStringId().test(productId);
  }

  @Override
  public List<Product> getAllProductsSortedByName(final String direction) {
    final Sort sort = evalSort(direction);
    return mProductRepository.findAll(sort);
  }

  private static Sort evalSort(final String direction) {
    return Sorts.byName(direction).getSort();
  }


  @Override
  public List<Product> findByName(final String pName) {
    final List<Product> foundByName = mProductRepository.findByName(pName);
    return foundByName;
  }
}
