package com.example.product_service.service;

import com.example.product_service.dto.ProductResponse;
import com.example.product_service.entity.Products;
import com.example.product_service.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService{

    @Autowired
    ProductsRepository repository;

    @Override
    public Page<Products> getAllProducts(int pageNo,
                                         int pageSize,
                                         String sortBy,
                                         String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase("DESC")
                                ?Sort.by(sortBy).descending()
                                :Sort.by(sortBy).ascending();

        Pageable pageable=PageRequest.of(pageNo-1,pageSize,sort);
        return repository.findAll(pageable);
    }
    @Override
    public void deleteProductsById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Products addProducts(Products products) {
        Products add=repository.save(products);
        return add;
    }

    @Override
    public ProductResponse getProductResponseByProductId(Long productId) {
        Products products=repository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product Not Found.."+productId));
        return new ProductResponse(
                products.getProductId(),
                products.getProductName(),
                products.getProductDescription(),
                products.getProductPrice()
        );
    }
}
