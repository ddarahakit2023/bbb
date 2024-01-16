package com.woof.api.orders.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Orders { //예약어 때문에 orders로 했어용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long phoneNumber; //예약자 전화번호
    private Integer time; //예약시간
    private String place;//픽업 장소
    private String reservation_status; //예약 상태
    private String orderDetails;

//    private String ceo_id;
//    private String user_id;
//    private String woof_manager_number;


//    //사용자 1 : 주문 N
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Member_idx")
//    private Ceo ceo;
//
//    //상품 1 : 주문 N
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Product_idx")
//    private ProductCeo productCeo;

//    //상세아이디 1 : 주문 N
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = )





}
