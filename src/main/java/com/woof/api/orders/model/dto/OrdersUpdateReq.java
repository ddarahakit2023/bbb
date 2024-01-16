package com.woof.api.orders.model.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrdersUpdateReq {
    private Long idx;

    private Long phoneNumber;
    private Integer time;
    private String place;
    private String reservation_status;
}