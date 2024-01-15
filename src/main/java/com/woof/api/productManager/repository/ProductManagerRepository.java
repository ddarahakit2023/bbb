package com.woof.api.productManager.repository;

//import com.example.demo.productManager.repository.querydsl.ProductRepositoryCustum;
import com.woof.api.productManager.model.ProductManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductManagerRepository extends JpaRepository<ProductManager, Long>
//        , ProductRepositoryCustum
{
    public Optional<ProductManager> findByName(String name);


//    @Query("SELECT p FROM ProductManager p " +
//            "JOIN FETCH p.productManagerImages " +
//            "JOIN FETCH p.brandIdx")
//    public List<ProductManager> findAllQuery();
}