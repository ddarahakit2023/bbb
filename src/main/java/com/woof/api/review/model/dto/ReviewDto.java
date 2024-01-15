package com.woof.api.review.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class ReviewDto {
    private final Integer id;
    private final String username;
    private final Integer productNumber; // Add productNumber field
    private final String text;
}








