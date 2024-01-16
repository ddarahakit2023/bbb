package com.woof.api.cart.repository;


import com.woof.api.cart.model.Cart;
import com.woof.api.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    public List<Cart> findAllByMember(Member member);
    public void deleteByMemberIdx(Long idx);
    // void인 이유는 이 메소드가 어떤 결과를 반환하지 않고 삭제하는 기능이기 때문
}
