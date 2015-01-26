package com.jambit.workshop.jib.spring.data.mongo.repo;

import com.jambit.workshop.jib.spring.data.mongo.domain.Customer;
import com.jambit.workshop.jib.spring.data.mongo.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("customerRepository")
@Transactional
public interface CustomerRepository extends MongoRepository<Customer, String> {
  @Query("{ 'email' : ?0 }")
  Customer findByEmail(String pEmail);

  @Query("{ 'firstName' : ?0, 'lastName' : ?1 }")
  List<Customer> findByFullname(String firstName, String lastName);
}
