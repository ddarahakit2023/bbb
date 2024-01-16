package com.woof.api.cart.controller;




import com.woof.api.cart.model.Cart;
import com.woof.api.cart.service.CartService;
import com.woof.api.member.model.Member;
import com.woof.api.productManager.model.ProductManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;

    @RequestMapping(method = RequestMethod.POST, value = "/in")
    public ResponseEntity create(Long productCeoIdx, Long productManagerIdx, String email) {

        cartService.cartIn(productCeoIdx, productManagerIdx, email);
        return ResponseEntity.ok().body("ok");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cartlist")
    public ResponseEntity<Object> cartList(/*@AuthenticationPrincipal*/ String email) {

        return ResponseEntity.ok().body(cartService.cartList(email));
    }


    // Ceo랑 매니저 아이디 따로 해야하나?
    @RequestMapping(method = RequestMethod.DELETE, value = "/cartremove/{idx}")
    public ResponseEntity cartRemove(/*@AuthenticationPrincipal*/@PathVariable Long idx) {
        cartService.cartRemove(idx);
        return ResponseEntity.ok().body("ok");
    }
}
