package com.woof.api.cart.repository;


import com.woof.api.cart.model.Cart;
import com.woof.api.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    //fetch join을 써서 해결을 해야한다
//    @Query("SELECT c FROM CART c " +
//            "JOIN FETCH c.member JOIN FETCH c.productCeo JOIN FETCH c.productManager " +
//            "WHERE c.member.idx = :memberIdx")
    List<Cart> findAllByMemberIdx(Long memberIdx);
}
