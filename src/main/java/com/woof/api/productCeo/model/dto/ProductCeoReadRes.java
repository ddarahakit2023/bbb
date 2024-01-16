package com.woof.api.productCeo.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductCeoReadRes {
    private Long idx;
//    private Long id;
    private String productName;
    private String storeName;
    private Integer phoneNumber;
//    Integer brandIdx;
    private Integer price;
    private String filename;
    private String contents;
}
