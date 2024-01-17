package com.woof.api.productManager.model;

import com.woof.api.orders.model.Orders;
import com.woof.api.review.model.Review;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class ProductManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String managerName;
    private String gender;
    private Integer phoneNumber;
    private Integer price;
    private String contents;

    @OneToMany(mappedBy = "order")
    private List<Orders> orders = new ArrayList<>();


    @OneToMany(mappedBy = "productManager", fetch = FetchType.EAGER)
    private List<ProductManagerImage> productManagerImages = new ArrayList<>();

    @OneToMany(mappedBy = "review")
    private List<Review> reviews = new ArrayList<>();

}
