package com.woof.api.cart.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {
    Long idx;
    Long productCeoIdx;
    Integer brandIdx;
    String name;
    String filename;
}
