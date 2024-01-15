package com.woof.api.review.service;


import com.example.demo.review.model.Review;

import com.example.demo.review.model.dto.ReviewDto;
import com.example.demo.review.repository.ProductRepository;
import com.example.demo.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {
    ReviewRepository reviewRepository;

    ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository , ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    public Integer create(ReviewDto reviewDto) {
        Review review = Review.builder()
                .username(reviewDto.getUsername())
                .text(reviewDto.getText())
                .productNumber(reviewDto.getProductNumber())
                .build();

        Review savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }

    public ReviewDto read(Integer id) {
        Review review = reviewRepository.findById(id).orElseThrow(()->new ReviewNotFoundException(""));

        return ReviewDto.builder()
                .id(review.getId())
                .text(review.getText())
                .username(review.getUsername())
                .productNumber(review.getProductNumber())
                .build();
    }

    public void update(Integer id, ReviewDto reviewDto) {
        Optional<Review> result = reviewRepository.findById(id);
        if(result.isPresent()) {
            Review review = result.get();
            review.setText(reviewDto.getText());
            reviewRepository.save(review);
        }

    }

    public void delete(Integer id) {
        reviewRepository.delete(Review.builder().id(id).build());
    }
}