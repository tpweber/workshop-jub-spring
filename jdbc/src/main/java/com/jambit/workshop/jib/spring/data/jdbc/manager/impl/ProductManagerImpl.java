package com.jambit.workshop.jib.spring.data.jdbc.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jambit.workshop.jib.spring.data.jdbc.dao.ProductDao;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Product;
import com.jambit.workshop.jib.spring.data.jdbc.manager.ProductManager;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;

@Service("productManager")
public class ProductManagerImpl implements ProductManager {

  @Autowired
  ProductDao mProductDao;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  @Override
  public Product getProduct(long id) {
    try {
      return mProductDao.selectProduct(id);
    } catch (DataAccessException dae) {
      throw new EntityNotFoundException("No product found for id[" + id + "] -> " + dae.getLocalizedMessage(), dae);
    }
  }
}
