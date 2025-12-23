package com.example.order_service.controller;

import com.example.order_service.entity.Order;
import com.example.order_service.service.OrderService;
import com.example.order_service.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

     private final OrderService service;

     @GetMapping
     public ResponseEntity<List<Order>> getAllOrders(){
         return ResponseEntity.ok(service.getAllOrders());
     }
     @GetMapping("/users/{userId}")
    public ResponseEntity<Order> getOrderByUserId(@PathVariable Long userId){
         Order byUserId=service.getOrderById(userId);
         return ResponseEntity.ok(byUserId);
     }
     @DeleteMapping("/{userId}")
     public ResponseEntity<Void> deleteByUserId(@PathVariable Long userId){
         service.deleteOrderById(userId);
         return ResponseEntity.ok().build();
     }
     @PostMapping("/users/{userId}")
    public ResponseEntity<Order> addOrder(@PathVariable Long userId){
         Order addOrder=service.addOrder(userId);
         return ResponseEntity.status(HttpStatus.CREATED).body(addOrder);
     }
}
