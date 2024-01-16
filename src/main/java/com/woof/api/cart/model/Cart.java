package com.woof.api.cart.model;


import com.woof.api.member.model.Member;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productManager.model.ProductManager;
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
    Long idx;


    // 즐겨찾기 : 멤버 = N : 1
    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;
    // 즐겨찾기 : 업체 = N : 1
    @ManyToOne
    @JoinColumn(name = "productCeo_idx")
    private ProductCeo productCeo;
    // 즐겨찾기 : 매니저 = N : 1
    @ManyToOne
    @JoinColumn(name = "productManager_idx")
    private ProductManager productManager;



}
