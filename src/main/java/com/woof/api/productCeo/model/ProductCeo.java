package com.woof.api.productCeo.model;

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
public class ProductCeo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String storeName;
    private String productName;
    private Integer phoneNumber;
    private Integer price;
    private String contents;

//    @OneToMany(mappedBy = "order")
//    private List<Order> orders = new ArrayList<>();           주문이랑 합칠 때 활성화


    @OneToMany(mappedBy = "product")
    private List<ProductCeoImage> productCeoImages = new ArrayList<>();

//    @OneToMany(mappedBy = "review")
//    private List<review> reviews = new ArrayList<>();         리뷰랑 합칠 때 활성화

}
