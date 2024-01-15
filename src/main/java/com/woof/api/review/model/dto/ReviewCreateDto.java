package com.woof.api.review.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewCreateDto {

    private Integer id;
    private final String username;
    private final String text;
    private final Integer productId;
}