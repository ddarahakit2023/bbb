package com.woof.api.cart.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartCreateReq {

    Long productCeoIdx;
}
