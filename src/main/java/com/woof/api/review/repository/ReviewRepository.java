package com.woof.api.review.repository;

import com.example.demo.review.model.Product;
import com.example.demo.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}

