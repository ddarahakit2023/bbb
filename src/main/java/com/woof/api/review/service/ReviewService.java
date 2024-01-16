package com.woof.api.review.service;


import com.woof.api.review.model.Review;
import com.woof.api.review.model.dto.ReviewDto;
import com.woof.api.review.repository.ProductRepository;
import com.woof.api.review.repository.ReviewRepository;
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

    public Long create(ReviewDto reviewDto) {
        Review review = Review.builder()
                .username(reviewDto.getUsername())
                .text(reviewDto.getText())
                .productNumber(reviewDto.getProductNumber())
                .build();

        Review savedReview = reviewRepository.save(review);
        return savedReview.getIdx();
    }

    public ReviewDto read(Long idx) {
        Review review = reviewRepository.findById(idx).orElseThrow(()->new ReviewNotFoundException(""));

        return ReviewDto.builder()
                .idx(review.getIdx())
                .text(review.getText())
                .username(review.getUsername())
                .productNumber(review.getProductNumber())
                .build();
    }

    public void update(Long idx, ReviewDto reviewDto) {
        Optional<Review> result = reviewRepository.findById(idx);
        if(result.isPresent()) {
            Review review = result.get();
            review.setText(reviewDto.getText());
            reviewRepository.save(review);
        }

    }

    public void delete(Long idx) {
        reviewRepository.delete(Review.builder().idx(idx).build());
    }
}