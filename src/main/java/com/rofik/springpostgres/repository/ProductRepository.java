package com.rofik.springpostgres.repository;

import com.rofik.springpostgres.domain.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findAllByCategoryId(Long categoryId);

    Product findByProductNameContaining(String productName);

}
