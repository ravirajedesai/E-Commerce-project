package com.example.order_service.feinClient;

import com.example.order_service.dto.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service")
public interface CartClient {

    @GetMapping("/carts/{userId}")
    CartResponse getCartByUserId(@PathVariable("userId") Long userId);

    @DeleteMapping("/carts/clearCart/{userId}")
    void clearCart(@PathVariable("userId") Long userId);
}
