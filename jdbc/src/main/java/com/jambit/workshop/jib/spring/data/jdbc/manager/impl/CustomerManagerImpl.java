package com.jambit.workshop.jib.spring.data.jdbc.manager.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jambit.workshop.jib.spring.data.jdbc.dao.CustomerDao;
import com.jambit.workshop.jib.spring.data.jdbc.domain.Customer;
import com.jambit.workshop.jib.spring.data.jdbc.manager.CustomerManager;
import com.jambit.workshop.jib.spring.data.shared.exception.EntityNotFoundException;

/**
 * Implementation of <code>CustomerManager</code>
 */
public class CustomerManagerImpl implements CustomerManager {
  private CustomerDao mCustomerDao;

  public void setCustomerDao(final CustomerDao dao) {
    mCustomerDao = dao;
  }

  @Transactional(propagation= Propagation.REQUIRED, readOnly=true)
  @Override
  public Customer getCustomer(long id) {
    try {
      return mCustomerDao.selectCustomer(id);
    } catch (DataAccessException dae) {
      throw new EntityNotFoundException("No customer found for id[" + id + "] -> " + dae.getLocalizedMessage(), dae);
    }
  }
}
