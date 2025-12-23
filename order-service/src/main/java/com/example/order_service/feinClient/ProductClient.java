package com.example.order_service.feinClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("product-service")
public interface ProductClient {
    @PostMapping("/products/{userId}")

}
