package com.woof.api.cart.service;


import com.woof.api.cart.model.Cart;
import com.woof.api.cart.model.dto.CartDto;
import com.woof.api.cart.repository.CartRepository;
import com.woof.api.common.Response;
import com.woof.api.member.model.Member;
import com.woof.api.member.repository.MemberRepository;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productManager.model.ProductManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    // 즐겨찾기 추가
    public Response cartIn(Long productCeoIdx, Long productManagerIdx, String email) {


        Optional<Member> member = memberRepository.findByEmail(email);
        // CartRepository를 사용하여 새로운 Cart 엔티티를 데이터베이스에 저장
        if (member.isPresent()) {
            Cart cart = cartRepository.save(Cart.builder()
                    .productCeo(ProductCeo.builder().idx(productCeoIdx).build())
                    .productManager(ProductManager.builder().idx(productManagerIdx).build())
                    .build());

            return Response.success("추가 성공");
        } else {
            return Response.error("2222");
        }
    }
    // 즐겨찾기 목록 조회
    public Response list (String email) {

        Optional<Member> member = memberRepository.findByEmail(email);


        if (member.isPresent()) {
            List<Cart> carts = cartRepository.findAllByMember(Member.builder().idx(member.get().getIdx()).build());

            List<CartDto> cartList = new ArrayList<>();
            for (Cart cart : carts) {
                ProductCeo productCeo = cart.getProductCeo();
                cartList.add(CartDto.builder()
                        .idx(cart.getIdx())
                        .productCeoName(cart.getProductCeo().getStoreName())// 업체명
                        .filename(cart.getProductCeo().getProductCeoImages().get(0).getFilename())// 업체 사진
                        .productManagerName(cart.getProductManager().getManagerName())// 매니저 이름
                        .build());

            }
            return Response.success("조회 성공");
        } else {

            return Response.error("0000");
        }
    }
//        return CartListRes.builder()
//                .code(1000)
//                .message("요청 성공.")
//                .success(true)
//                .isSuccess(true)
//                .result(cartDtos)
//                .build();


        @Transactional
        public Response remove (Long idx, Member member) {
            // CartRepository를 사용하여 id 및 관련 멤버로 즐겨찾기 삭제
            cartRepository.deleteByIdxAndMember(idx ,member);

            return Response.success("삭제 성공");
        }

    }

