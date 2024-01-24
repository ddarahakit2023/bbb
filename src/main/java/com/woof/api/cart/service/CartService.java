package com.woof.api.cart.service;


import com.woof.api.cart.model.Cart;
import com.woof.api.cart.model.dto.*;
import com.woof.api.cart.repository.CartRepository;
import com.woof.api.common.Response;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.repository.MemberRepository;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productCeo.repository.ProductCeoRepository;
import com.woof.api.productManager.model.ProductManager;
import com.woof.api.productManager.repository.ProductManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    // TODO: 에러코드 수정
    public Response cartIn(CartCreateReq request) {
        // repo .save();
        Cart cart = cartRepository.save(Cart.builder()
                .member(Member.builder()
                        .idx(request.getMemberIdx())
                        .build())
                .productCeo(ProductCeo.builder()
                        .idx(request.getProductCeoIdx())
                        .build())
                .productManager(ProductManager.builder()
                        .idx(request.getProductManagerIdx())
                        .build())
                .build());

        Response<Object> result = Response.builder()
                .resultCode("등록이 성공됐습니다.")
                .result(CartCreateRes.builder()
                        .cartIdx(cart.getIdx())
                        .memberIdx(cart.getMember().getIdx())
                        .productCeoIdx(cart.getProductCeo().getIdx())
                        .productManagerIdx(cart.getProductManager().getIdx())
                        .build())
                .build();
        return result;

//        Optional <Member> member = memberRepository.findBy(idx);
        // CartRepository를 사용하여 새로운 Cart 엔티티를 데이터베이스에 저장
//        if (member.isPresent()) {
//            Cart cart = cartRepository.save(Cart.builder()
//                    .productCeo(ProductCeo.builder().idx(productCeoIdx).build())
//                    .productManager(ProductManager.builder().idx(productManagerIdx).build())
//                    .member(member.get())
//                    .build());
//
//
//
//            return Response.success("추가 성공");
//        } else {
//            return Response.error("2222");
//        }
    }

//     즐겨찾기 목록 조회
    public List<CartListRes> cartList(Long memberIdx) {
        List<Cart> result = cartRepository.findAllByMemberIdx(memberIdx);
        List<CartListRes> list = new ArrayList<>();

        for (Cart cart : result) {
            CartListRes cartListRes = CartListRes.builder()
                    .cartIdx(cart.getIdx())
                    .memberIdx(cart.getMember().getIdx())
                    .productCeoIdx(cart.getProductCeo().getIdx())
                    .productCeoStoreName(cart.getProductCeo().getStoreName())
                    .productManagerIdx(cart.getProductManager().getIdx())
                    .productMangerName(cart.getProductManager().getManagerName())
                    .build();
            list.add(cartListRes);
        }
        return list;

    }


    // @Transactional
    public void cartRemove(Long idx) {
        cartRepository.deleteById(idx);
    }

}

