package com.woof.api.review.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewReadDto
{
    private Long idx;
    private final String username;
    private final String text;
    private final Integer productId;
}