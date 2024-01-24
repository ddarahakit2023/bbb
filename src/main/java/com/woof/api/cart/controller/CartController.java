package com.woof.api.cart.controller;




import com.woof.api.cart.model.dto.CartCreateReq;
import com.woof.api.cart.service.CartService;
import com.woof.api.common.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;

    @RequestMapping(method = RequestMethod.POST, value = "/in")
    public ResponseEntity create(@RequestBody CartCreateReq cartCreateReq) {
        Response response = cartService.cartIn(cartCreateReq);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cartlist")
    public ResponseEntity<Object> cartList(Long memberIdx) {

        return ResponseEntity.ok().body(cartService.cartList(memberIdx));
    }



    @RequestMapping(method = RequestMethod.DELETE, value = "/cartremove/{idx}")
    public ResponseEntity cartRemove(/*@AuthenticationPrincipal*/@PathVariable Long idx) {

        cartService.cartRemove(idx);
        return ResponseEntity.ok().body("ok");
    }
}
