package com.woof.cart.model;


import com.example.Woof_2nd.productCeo.model.ProductCeo;
import com.example.demo.member.model.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 즐겨찾기 : 멤버 = N : 1
    @ManyToOne
    @JoinColumn(name = "Member_id")
    private Member member;
    // 즐겨찾기 : 업체 = N : 1
    @ManyToOne
    @JoinColumn(name = "Product_id")
    private ProductCeo productCeo;


}
