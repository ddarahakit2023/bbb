package com.woof.api.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResDto {
    private  Long reviewIdx;

    private String status;
    private  String username;
    private  Integer productNumber; // Add productNumber field
    private  String text;

    private Long ordersIdx;
}
