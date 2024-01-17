package com.woof.api.cart.repository;


import com.woof.api.cart.model.Cart;
import com.woof.api.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    public List<Cart> findAllByMember(Member member);


}
