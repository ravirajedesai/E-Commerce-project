package com.example.order_service.service;


import com.example.order_service.dto.CartItemResponse;
import com.example.order_service.dto.CartResponse;
import com.example.order_service.dto.UserResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderItems;
import com.example.order_service.exception.CartNotFound;
import com.example.order_service.exception.OrderNotFound;
import com.example.order_service.feinClient.CartClient;
import com.example.order_service.feinClient.ProductClient;
import com.example.order_service.feinClient.UserClient;
import com.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository repository;

    @Autowired
    UserClient userClient;

    @Autowired
    CartClient cartClient;

    @Autowired
    ProductClient productClient;

    @Override
    public List<Order> getAllOrders() {
        return repository.findAll();
    }
    @Override
    public Order getOrderById(Long userId) {
        Order byid=repository.findByUserId(userId)
                .orElseThrow(()->new OrderNotFound("Order Not Found.."+userId));
        return byid;
    }
    @Override
    public void deleteOrderById(Long userId) {
    repository.deleteById(userId);
    }
    @Override
    public Order addOrder(Long userId) {
        UserResponse userResponse=userClient.getUserByUserId(userId);

        CartResponse cartResponse=cartClient.getCartByUserId(userId);

        if (cartResponse.getItems() == null || cartResponse.getItems().isEmpty()) {
            throw new CartNotFound("Cart is empty. Cannot place order.");
        }
        try{
        for(CartItemResponse item : cartResponse.getItems()){
            productClient.reduceProductStock(
                    item.getProductId(),
                    item.getProductQuantity()
            );
        }} catch (Exception e) {
            throw new RuntimeException("Order Failed..Product stock issue.");
        }

        Order order=new Order();
        order.setUserId(userId);
        order.setOrderStatus("Placed..");
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(cartResponse.getTotalAmount());

        List<OrderItems> orderItems=new ArrayList<>();

        for (CartItemResponse item:cartResponse.getItems()){

            OrderItems orderItems1=new OrderItems();
            orderItems1.setOrder(order);
            orderItems1.setProductId(item.getProductId());
            orderItems1.setProductName(item.getProductName());
            orderItems1.setProductPrice(item.getProductPrice());
            orderItems1.setProductQuantity(item.getProductQuantity());
            orderItems1.setTotalAmount(item.getTotalAmount());

            orderItems.add(orderItems1);
        }
        order.setItems(orderItems);

        Order saveOrder=repository.save(order);

        cartClient.clearCart(userId);
        return saveOrder;
    }
}
