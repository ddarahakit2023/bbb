package com.woof.api.review.repository;


import com.woof.api.review.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 특별한 메서드가 필요하면 여기에 추가
}


