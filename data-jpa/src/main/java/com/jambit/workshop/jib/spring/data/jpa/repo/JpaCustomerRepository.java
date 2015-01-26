package com.jambit.workshop.jib.spring.data.jpa.repo;

import com.jambit.workshop.jib.spring.data.jpa.domain.Customer;
import com.jambit.workshop.jib.spring.data.jpa.domain.EmailAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * JPA based implementation of <code>CustomerRepository</code>
 */
@Repository
class JpaCustomerRepository implements CustomerRepository {

    private static final String SELECT_BY_EMAIL = "select c from Customer c where c.emailAddress = :email";

    @Autowired
    @PersistenceContext
    private EntityManager mEntityManager;

    @Override
    public Customer findOne(Long id) {
        return mEntityManager.find(Customer.class, id);
    }

    @Override
    public Customer save(Customer customer) {
        if (canBeSaved(customer)) {
            mEntityManager.persist(customer);
            return customer;
        }
        return mEntityManager.merge(customer);
    }

    private boolean canBeSaved(final Customer customer) {
        return null != customer && null != customer.getId();
    }

    @Override
    public Customer findByEmailAddress(final EmailAddress emailAddress) {

        final TypedQuery<Customer> query = mEntityManager.createQuery(SELECT_BY_EMAIL, Customer.class);
        query.setParameter(EmailAddress.ATTRIBUTE_EMAIL, emailAddress);
        return query.getSingleResult();
    }
}
