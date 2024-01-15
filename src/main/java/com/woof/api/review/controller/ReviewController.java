package com.woof.api.review.controller;

import com.example.demo.review.model.dto.ReviewDto;
import com.example.demo.review.service.ReviewNotFoundException;
import com.example.demo.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Integer> create(@RequestBody ReviewDto reviewDto) {
        Integer reviewId = reviewService.create(reviewDto);
        return ResponseEntity.ok().body(reviewId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity read(Integer id) {
        return ResponseEntity.ok().body(reviewService.read(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody ReviewDto reviewDto) {
        try {
            reviewService.update(id, reviewDto);
            return ResponseEntity.ok().body("리뷰가 성공적으로 업데이트되었습니다.");
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Integer id) {
        reviewService.delete(id);
        return ResponseEntity.ok().body("리뷰가 삭제되었습니다.");
    }
}