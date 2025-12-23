package com.example.cart.feignClient;

import com.example.cart.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/products/{productId}")
    ProductResponse getProductById(@PathVariable Long productId);
}
