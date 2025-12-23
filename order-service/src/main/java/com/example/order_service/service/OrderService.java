package com.example.order_service.service;

import com.example.order_service.entity.Order;

import java.util.List;


public interface OrderService {

    List<Order> getAllOrders();
    Order getOrderById(Long userId);
    void deleteOrderById(Long userId);

    Order addOrder(Long userId);
}
