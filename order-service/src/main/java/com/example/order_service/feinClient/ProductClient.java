package com.example.order_service.feinClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("product-service")
public interface ProductClient {

    @PutMapping("/products/{productId}/reduce-stock/{quantity}")
    Boolean reduceProductStock(@PathVariable("productId") Long productID,
                            @PathVariable("quantity") Integer quantity);
}
