package com.example.order_service.feinClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("product-service")
public interface ProductClient {

    @PostMapping("/products/reduce-stock")
    void reduceProductStock(@PathVariable Long productID,
                            @PathVariable Double productStock);
}
