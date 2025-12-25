package com.example.cart.service;

import com.example.cart.dto.CartResponse;
import com.example.cart.entity.Cart;

import java.util.List;

public interface CartServices {

    CartResponse showCartByUserId(Long userId);

    Cart addItemsToCart(Long userId,Long productId,Integer quantity);

    Cart removeItemFromCart(Long userId,Long productId);

    void clearCart(Long userId);
}
