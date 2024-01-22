package com.woof.api.orders.model;


import com.woof.api.member.model.entity.Ceo;
import com.woof.api.member.model.entity.Member;
import com.woof.api.payment.model.Payment;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productManager.model.ProductManager;
import lombok.*;

import javax.persistence.*;

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



    //업체 1 : 주문 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ceo_idx")
    private Ceo ceo;
//

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCeo_idx")
    private ProductCeo productCeo;

    //매니저 1 : 주문 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productManager_idx")
    private ProductManager productManager;

    //고객 1 : 주문 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    //결제 1 : 주문 N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_idx")
    private Payment payment;





}
