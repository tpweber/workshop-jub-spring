package com.jambit.workshop.jib.spring.data.mongo.repo;

import com.jambit.workshop.jib.spring.data.mongo.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface OrderRepository extends MongoRepository<Order, String> {
  
}
