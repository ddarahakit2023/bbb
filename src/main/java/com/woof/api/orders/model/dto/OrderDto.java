package com.woof.api.orders.model.dto;

import com.woof.member.ceo.model.response.PostCreateCeoRes;
import com.woof.productCeo.model.ProductCeo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long idx;

    private Long phoneNumber; //예약자 전화번호
    private Integer time; //예약시간
    private String place;//픽업 장소
    private String reservation_status; //예약 상태
    private ProductCeo productCeo; //상품
    private PostCreateCeoRes postCreateCeoRes; //사용자
}
