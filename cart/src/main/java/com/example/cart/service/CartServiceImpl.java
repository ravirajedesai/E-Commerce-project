package com.example.cart.service;

import com.example.cart.dto.CartItemResponse;
import com.example.cart.dto.CartResponse;
import com.example.cart.dto.ProductResponse;
import com.example.cart.dto.UserResponse;
import com.example.cart.entity.Cart;
import com.example.cart.entity.CartItem;
import com.example.cart.exceptions.CartNotFound;
import com.example.cart.exceptions.ProductNotFound;
import com.example.cart.exceptions.UserNotFound;
import com.example.cart.feignClient.ProductClient;
import com.example.cart.feignClient.UserClient;
import com.example.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartServices{

    @Autowired
    CartRepository repository;
    @Autowired
    UserClient userClient;
    @Autowired
    ProductClient productClient;

    @Override
    public CartResponse showCartByUserId(Long userId) {
        Cart cartById=repository.findByUserId(userId)
                .orElseThrow(()->new CartNotFound("Your Cart is Empty.."+userId));

        List<CartItemResponse> items=cartById.getItems().stream()
                .map(item->new CartItemResponse(
                        item.getProductId(),
                        item.getProductName(),
                        item.getProductPrice(),
                        item.getProductQuantity(),
                        item.getTotalAmount()
                ))
                .toList();

        return new CartResponse(
                cartById.getUserId(),
                cartById.getTotalAmount(),
                items
        );
    }
    @Override
    public Cart removeItemFromCart(Long userId, Long productId) {
        Cart cart=repository
                .findByUserId(userId)
                .orElseThrow(()->
                        new UserNotFound("User Not Found.."+userId));
        boolean removed=cart
                .getItems()
                .removeIf(i->i.getProductId().equals(productId));
        if(!removed){
            throw new ProductNotFound("Product Not Removed..");
        }
        Double total=cart
                .getItems()
                .stream()
                .mapToDouble(CartItem::getTotalAmount)
                .sum();
        cart.setTotalAmount(total);
        return repository.save(cart);
    }

    @Override
    public Cart addItemsToCart(Long userId, Long productId, Double quantity) {
        try {
            UserResponse userResponse = userClient.getUserByUserId(userId);
        }catch (Exception e){
            throw new UserNotFound("User Not Found"+userId);
        }
        Cart cart=repository.findByUserId(userId)
                .orElseGet(()->{
                    Cart newcart= new Cart();
                    newcart.setUserId(userId);
                    newcart.setItems(new ArrayList<>());
                    newcart.setTotalAmount(0.0);
                    return newcart;
                });

        ProductResponse productResponse = productClient.getProductById(productId);

        Optional<CartItem> existingItem= cart
                        .getItems()
                        .stream()
                        .filter(i->i.getProductId().equals(productId))
                        .findFirst();
        if(existingItem.isPresent()){

            CartItem item=existingItem.get();

            Double updatedQuantity=item.getProductQuantity() + quantity;
            item.setProductQuantity(updatedQuantity);
            item.setTotalAmount(updatedQuantity * item.getProductPrice());

        }else {
            CartItem item=new CartItem();
            item.setCart(cart);
            item.setProductId(productId);
            item.setProductName(productResponse.getProductName());
            item.setProductPrice(productResponse.getProductPrice());
            item.setProductQuantity(quantity);
            item.setTotalAmount(productResponse.getProductPrice() * quantity);

            cart.getItems().add(item);
        }
        double total=cart
                .getItems()
                .stream()
                .mapToDouble(CartItem::getTotalAmount)
                .sum();
        cart.setTotalAmount(total);

        return repository.save(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart=repository.findByUserId(userId)
                .orElseThrow(()->new CartNotFound("Cart Not Found.."+userId));
        repository.delete(cart);
    }
}
