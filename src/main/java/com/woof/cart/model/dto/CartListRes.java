package com.woof.cart.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CartListRes {
    Boolean isSuccess;
    Boolean success;
    Integer code;
    String message;
    List<CartDto> result;

}
