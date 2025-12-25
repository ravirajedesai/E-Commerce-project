package com.example.cart.controller;

import com.example.cart.dto.CartResponse;
import com.example.cart.entity.Cart;
import com.example.cart.service.CartServiceImpl;
import com.example.cart.service.CartServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartServices services;

    @GetMapping
    public ResponseEntity<String> getAllCarts(){
        return ResponseEntity.ok("should enter User Id for getting Cart..");
    }
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> showCartByUserId(@PathVariable Long userId){
        return ResponseEntity.ok( services.showCartByUserId(userId));
    }
    @DeleteMapping("/clearCart/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId){
        services.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable Long userId,
                                                      @PathVariable Long productId){
        Cart removed=services.removeItemFromCart(userId,productId);
        return ResponseEntity.ok(removed);
    }
    @PostMapping("/{userId}/{productId}/{quantity}")
    public ResponseEntity<Cart> addItemsToCart(@PathVariable Long userId,
                                               @PathVariable Long productId,
                                               @PathVariable Integer quantity){
        Cart add=services.addItemsToCart(userId,productId,quantity);
        return ResponseEntity.ok(add);
    }

}
