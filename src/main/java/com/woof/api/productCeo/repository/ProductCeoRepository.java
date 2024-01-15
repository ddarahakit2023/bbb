package com.woof.api.productCeo.repository;

import com.woof.api.productCeo.model.ProductCeo;
//import com.example.demo.productManager.repository.querydsl.ProductRepositoryCustum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCeoRepository extends JpaRepository<ProductCeo, Long>
//        , ProductRepositoryCustum
{
    public Optional<ProductCeo> findByName(String name);


//    @Query("SELECT p FROM ProductManager p " +
//            "JOIN FETCH p.productManagerImages " +
//            "JOIN FETCH p.brandIdx")
//    public List<ProductManager> findAllQuery();
}