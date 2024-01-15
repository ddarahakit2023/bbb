package com.woof.api.orders.service;

import com.woof.api.orders.model.dto.OrderDto;
import com.woof.api.orders.model.dto.OrdersListRes;
import com.woof.api.orders.model.dto.OrdersReadRes;
import com.woof.api.orders.model.dto.OrdersReadRes2;
import com.woof.api.orders.repository.OrderRepository;
import com.woof.member.ceo.model.response.PostCreateCeoRes;
import com.woof.productCeo.model.ProductCeo;
import com.woof.productCeo.model.dto.ProductCeoReadRes;
import com.woof.api.orders.model.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void create(Long memberid, Long productid, Orders orders) {
        orderRepository.save(Orders.builder()
                .productCeo(ProductCeo.builder().idx(productid).build())

                .postCreateCeoRes(PostCreateCeoRes.builder().idx(memberid).build())

                .phoneNumber(orders.getPhoneNumber())
                .time(orders.getTime())
                .orderDetails(orders.getOrderDetails())
                .place(orders.getPlace())
                .reservation_status(orders.getReservation_status())
                .build());
    }

    public OrdersListRes list() {
        List<Orders> result = orderRepository.findAll();
        List<OrdersReadRes> orderDtos = new ArrayList<>();

        for (Orders orders:result) {
            ProductCeo productCeo = orders.getProductCeo();
            PostCreateCeoRes createCeoRes = orders.getPostCreateCeoRes();

            List<OrdersReadRes> ordersReadRes = new ArrayList<>();

            ProductCeoReadRes productCeoReadRes = ProductCeoReadRes.builder()
                    .idx(productCeo.getIdx())
                    .storeName(productCeo.getStoreName())
                    .price(productCeo.getPrice())
                    .build();

            PostCreateCeoRes postCreateCeoRes = PostCreateCeoRes.builder()
                    .idx(createCeoRes.getIdx())
                    .ceoname(createCeoRes.getCeoname())
                    .email(createCeoRes.getEmail())
                    .build();

            OrdersReadRes ordersReadRes1 = OrdersReadRes.builder()
                    .idx(orders.getIdx())
                    .place(orders.getPlace())
                    .time(orders.getTime())
                    .phoneNumber(orders.getPhoneNumber())
                    .reservation_status(orders.getReservation_status())
                    .build();

            orderDtos.add(ordersReadRes1);
        }


        return OrdersListRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result(orderDtos)
                .build();
    }

    public OrdersReadRes2 read(Long id) {
        Optional<Orders> result = orderRepository.findById(id);
        List<OrdersReadRes2> orderDto2 = new ArrayList<>();

        if (result.isPresent()) {
            Orders orders = result.get();

//            return OrderDto.builder()
//                    .idx(orders.getIdx())
//                    .place(orders.getPlace())
//                    .time(orders.getTime())
//                    .phoneNumber(orders.getPhoneNumber())
//                    .reservation_status(orders.getReservation_status())
//                    .build();


            ProductCeo productCeo = ProductCeo.builder()
                    .idx(productCeo.getIdx())
                    .productName(productCeo.getStoreName())
                    .storeName(productCeo.getStoreName())
                    .phoneNumber(productCeo.getPhoneNumber())
                    .price(productCeo.getPrice())
                    .contents(productCeo.getContents())
                    .filename(filenames)
                    .build();

            OrdersReadRes ordersReadRes = OrdersReadRes.builder()
                    .idx(orders.getIdx())
                    .phoneNumber(orders.getPhoneNumber())
                    .time(orders.getTime())
                    .place(orders.getPlace())
                    .reservation_status(orders.getReservation_status())
                    .build();

            return OrdersReadRes2.builder()
                    .code(1000)
                    .message("요청 성공.")
                    .success(true)
                    .isSuccess(true)
                    .result(ordersReadRes)
                    .build();
        } else {
            return null;

        }
    }

    public void update(OrderDto orderDto) {
        Optional<Orders> result = orderRepository.findById(orderDto.getIdx());

        if(result.isPresent()) {
            Orders orders = result.get();
            orderRepository.save(orders);
        }

    }

    public void delete(Long idx) {
        orderRepository.delete(
                Orders.builder()
                        .idx(idx)
                        .build());
    }

}
