package com.woof.api.orders.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class OrdersListRes2 {
    private Long idx;
    private String name;
    private Long phoneNumber;
    private Integer time;
    private String place;
}
