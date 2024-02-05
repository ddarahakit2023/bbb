package com.woof.api.orders.controller;


import com.woof.api.orders.model.Orders;
import com.woof.api.orders.model.dto.*;
import com.woof.api.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
@CrossOrigin("*")
public class OrderController {
    private final OrderService orderService;


    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@RequestBody OrderDto orderDto){
        orderService.create(orderDto);

        return ResponseEntity.ok().body("예약에 성공하였습니다");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(){
        return ResponseEntity.ok().body(orderService.list());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity read(Long idx){
        return ResponseEntity.ok().body(orderService.read(idx));
    }



    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity<OrdersReadRes2> update(@RequestBody OrdersUpdateReq ordersUpdateReq) {
        //ResponseEntity 반환, orderDto를 매개변수로 받아옴
        OrdersReadRes2 result = orderService.update(ordersUpdateReq);
        //orderService의 업데이트 메소드에 orderDto를 받아옴

        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long idx){
        orderService.delete(idx);

        return ResponseEntity.ok().body("삭제를 성공했습니다.");
    }

    @GetMapping("/{memberIdx}")
    public ResponseEntity getOrders(@PathVariable Long memberIdx) {
        List<Dk> orders = orderService.getOrdersByMemberIdx(memberIdx);
        return ResponseEntity.ok().body(orders);
    }



}
