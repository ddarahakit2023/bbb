package com.woof.api.review.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Builder
@Data
@Getter
public class ReviewDto {

    @NotNull
    private final Long idx;
    @NotNull
    private final String username;
    @NotNull
    private final Integer productNumber; // Add productNumber field
    @NotNull
    private final String text;
}








