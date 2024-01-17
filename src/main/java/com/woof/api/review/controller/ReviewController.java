package com.woof.api.review.controller;


import com.woof.api.review.model.dto.ReviewDto;
import com.woof.api.review.model.dto.ReviewResDto;
import com.woof.api.review.service.ReviewNotFoundException;
import com.woof.api.review.service.ReviewService;
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
    public ResponseEntity create(@RequestBody ReviewDto reviewDto) {
        ReviewResDto reviewResDto = reviewService.create(reviewDto);
        return ResponseEntity.ok().body(reviewResDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity read(Long idx) {
        return ResponseEntity.ok().body(reviewService.read(idx));
    }

    @PatchMapping("/update/{idx}")
    public ResponseEntity<String> update(@PathVariable Long idx, @RequestBody ReviewDto reviewDto) {
        try {
            reviewService.update(idx, reviewDto);
            return ResponseEntity.ok().body("리뷰가 성공적으로 업데이트되었습니다.");
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long idx) {
        reviewService.delete(idx);
        return ResponseEntity.ok().body("리뷰가 삭제되었습니다.");
    }
}