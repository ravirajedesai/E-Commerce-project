package com.example.product_service.repository;

import com.example.product_service.dto.ProductResponse;
import com.example.product_service.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products,Long> {
}
