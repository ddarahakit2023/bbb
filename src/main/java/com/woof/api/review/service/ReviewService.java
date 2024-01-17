package com.woof.api.review.service;


import com.woof.api.review.model.Product;
import com.woof.api.review.model.Review;
import com.woof.api.review.model.dto.ReviewDto;
import com.woof.api.review.model.dto.ReviewResDto;
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

    public ReviewResDto create(ReviewDto reviewDto) {
        Review review = Review.builder()
                .username(reviewDto.getUsername())
                .text(reviewDto.getText())
                .productNumber(reviewDto.getProductNumber())
                .product(Product.builder()
                        .idx(reviewDto.getProductIdx())
                        .build())
                .build();

        Review result = reviewRepository.save(review);


        return ReviewResDto.builder()
                .reviewIdx(result.getIdx())
                .username(result.getUsername())
                .text(result.getText())
                .productNumber(result.getProductNumber())
                .productIdx(result.getProduct().getIdx())
                .status("리뷰가 생성되었습니다.")
                .build();
    }

    public ReviewDto read(Long idx) {
        Review review = reviewRepository.findById(idx).orElseThrow(()->new ReviewNotFoundException(""));

        return ReviewDto.builder()
                .idx(review.getIdx())
                .productIdx(review.getProduct().getIdx())
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