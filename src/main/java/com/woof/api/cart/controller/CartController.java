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
    public ResponseEntity create(@RequestBody Long productCeoIdx, Long productManagerIdx, String email) {
                             // Member의 UserDetails 객체에 접근가능
        cartService.cartIn(productCeoIdx, productManagerIdx,email);
        return ResponseEntity.ok().body("ok");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(@AuthenticationPrincipal Member member) {

        return ResponseEntity.ok().body(cartService.list(member.getEmail()));
    }


    // Ceo랑 매니저 아이디 따로 해야하나?
    @RequestMapping(method = RequestMethod.DELETE, value = "/cancel/{idx}")
    public ResponseEntity remove(@AuthenticationPrincipal Member member,@PathVariable Long idx) {
        cartService.remove(idx, member);
        return ResponseEntity.ok().body("ok");
    }
}
