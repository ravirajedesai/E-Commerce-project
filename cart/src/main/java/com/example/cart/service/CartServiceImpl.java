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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartServices{

    private final CartRepository repository;

    private final UserClient userClient;

    private final ProductClient productClient;

    @Override
    public CartResponse showCartByUserId(Long userId) {
        Cart cart=repository.findByUserId(userId)
                .orElseThrow(()->new CartNotFound("Your Cart is Empty.."+userId));

        if(cart.getItems().isEmpty()){
            return new CartResponse(userId,0.0,List.of());
        }
        List<CartItemResponse> items=cart
                                        .getItems()
                                        .stream()
                                        .map(item->new CartItemResponse(
                                                item.getProductId(),
                                                item.getProductName(),
                                                item.getProductPrice(),
                                                item.getProductQuantity(),
                                                item.getTotalAmount()
                                        ))
                                        .toList();

        return new CartResponse(
                cart.getUserId(),
                cart.getTotalAmount(),
                items
        );
    }
    @Override
    @Transactional
    public Cart removeItemFromCart(Long userId, Long productId) {
        Cart cart=repository
                .findByUserId(userId)
                .orElseThrow(()->new CartNotFound("Cart Not Found.."+userId));
        boolean removed=cart
                .getItems()
                .removeIf(i->i.getProductId().equals(productId));
        if(!removed){
            throw new ProductNotFound("Product Not Removed.."+productId);
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
    public Cart addItemsToCart(Long userId, Long productId, Integer quantity) {
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

            Integer updatedQuantity=item.getProductQuantity() + quantity;
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
        cart.getItems().clear();
        repository.save(cart);
    }
}
