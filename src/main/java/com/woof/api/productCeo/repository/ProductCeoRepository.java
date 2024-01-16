package com.woof.api.productCeo.repository;

import com.woof.api.productCeo.model.ProductCeo;
//import com.example.demo.productManager.repository.querydsl.ProductRepositoryCustum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCeoRepository extends JpaRepository<ProductCeo, Long> {
    public Optional<ProductCeo> findByProductName(String productName);
}