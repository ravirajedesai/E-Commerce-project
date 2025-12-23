package com.example.product_service.service;

import com.example.product_service.dto.ProductResponse;
import com.example.product_service.entity.Products;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductsService {
    Page<Products> getAllProducts(int pageNo,
                                  int PageSize,
                                  String sortBy,
                                  String sortDir);
    void deleteProductsById(Long id);
    Products addProducts(Products products);

    ProductResponse getProductResponseByProductId(Long productId);
}
