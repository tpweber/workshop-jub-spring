package com.jambit.workshop.jib.spring.data.jpa.repo;

import com.jambit.workshop.jib.spring.data.jpa.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository for Product
 */
public interface ProductRepository extends JpaRepository<Product, Long>, QueryDslPredicateExecutor<Product> {

    Page<Product> findByDescriptionContaining(String description, Pageable pageable);

    @Query("select p from Product p where p.attributes[?1] = ?2")
    List<Product> findByAttributeAndValue(String attribute, String value);

    @Query("select p from Product p where p.price >= :from and p.price <= :to")
    List<Product> findByPriceRange(@Param("from") BigDecimal param1, @Param("to") BigDecimal param2);

    @Query("select p from Product p where p.price >= ?1")
    List<Product> findFromPrice(BigDecimal from);
}
