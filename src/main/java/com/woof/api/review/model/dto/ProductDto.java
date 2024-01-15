package com.woof.api.review.model.dto;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
public class ProductDto {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @NotNull
    private String productname;
}
