package com.jambit.workshop.jib.spring.data.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jambit.workshop.jib.spring.data.mongo.domain.Product;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("productRepository")
@Transactional
public interface  ProductRepository extends MongoRepository<Product, String>, QueryDslPredicateExecutor<Product> {
  @Query("{ 'name' : ?0 }")
  List<Product> findByName(String pName);
}
