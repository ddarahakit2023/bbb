package com.woof.api.orders.model.dto;

import com.woof.api.orders.model.Orders;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class MyListRes {
    private String message;
    private Object data;

    public MyListRes(String message, Object data) {
        this.message = message;
        this.data = data;
    }

}
