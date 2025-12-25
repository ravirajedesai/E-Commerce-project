package com.example.product_service.controller;

import com.example.product_service.dto.ProductResponse;
import com.example.product_service.entity.Products;
import com.example.product_service.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService service;

    @GetMapping
    public ResponseEntity<Page<Products>>
    getAllProducts(@RequestParam(defaultValue = "1") int pageNo,
                   @RequestParam(defaultValue = "4") int pageSize,
                   @RequestParam(defaultValue = "productName") String sortBy,
                   @RequestParam(defaultValue = "asc") String sortDir){
    return ResponseEntity.status(HttpStatus.OK)
            .body(service.getAllProducts(pageNo,pageSize,sortBy,sortDir));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteProductsById(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<Products> addProducts(@RequestBody Products products){
        Products add=service.addProducts(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(add);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId){
        ProductResponse getById=service. getProductResponseByProductId(productId);
        return ResponseEntity.ok(getById);
    }
    @PutMapping("/{productId}/reduce-stock/{quantity}")
    public ResponseEntity<Boolean> reduceProductStock(
            @PathVariable Long productId,
            @PathVariable Integer quantity
    ){
        boolean reduced=service.reduceProductStock(productId,quantity);
        return ResponseEntity.ok(reduced);
    }
}
