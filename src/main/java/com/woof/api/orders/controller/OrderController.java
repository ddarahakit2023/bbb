package com.woof.api.orders.controller;

import com.woof.api.orders.model.Orders;
import com.woof.api.orders.model.dto.OrderDto;
import com.woof.api.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(Long memberid, Long productid, Orders orders){
        orderService.create(memberid, productid, orders);

        return ResponseEntity.ok().body("예약 성공");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(){
        return ResponseEntity.ok().body(orderService.list());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity read(Long id){
        return ResponseEntity.ok().body(orderService.read(id));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(OrderDto orderDto){
        orderService.update(orderDto);

        return ResponseEntity.ok().body("주문 수정");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long idx){
        orderService.delete(idx);

        return ResponseEntity.ok().body("주문 삭제");
    }





}
