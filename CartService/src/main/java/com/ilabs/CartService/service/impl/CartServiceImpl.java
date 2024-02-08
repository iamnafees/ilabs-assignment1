package com.ilabs.CartService.service.impl;

import com.ilabs.CartService.dto.ItemDetailsDto;
import com.ilabs.CartService.entity.Cart;
import com.ilabs.CartService.entity.Cart_Item;
import com.ilabs.CartService.exception.NotValidQuantityException;
import com.ilabs.CartService.jwt.JwtUtil;
import com.ilabs.CartService.repository.CartItemRepository;
import com.ilabs.CartService.repository.CartRepository;
import com.ilabs.CartService.service.CartService;
import io.jsonwebtoken.Jwt;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    WebClient.Builder builder = WebClient.builder();


    @Override
    public String addItemToCart(Long itemId, int userId, int quantity) {

        if(JwtUtil.userId!=userId){
            return "not a valid user";
        }

        ItemDetailsDto itemDetailsDto = builder.build()
                .get()
                .uri("http://localhost:8085/api/v1/item/verify/{id}", itemId)
                .retrieve()
                .bodyToMono(ItemDetailsDto.class).block();
        if (itemDetailsDto == null) {
            throw new EntityNotFoundException("BadRequest");
        }
//        System.out.println(itemDetailsDto.getQuantity());
        if (itemDetailsDto.getQuantity() < quantity) {
            throw new NotValidQuantityException("Order quantity exceeds the Stock ,Available Quantity" + " " + itemDetailsDto.getQuantity());
        }
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            Cart cart1 = new Cart();
            cart1.setUserId(userId);
            Cart cart2 = cartRepository.save(cart1);
            Cart_Item cartItem = new Cart_Item();
            cartItem.setItemId(itemId);
            cartItem.setQuantity(quantity);
            cartItem.setCartId(cart2);
            cartItemRepository.save(cartItem);
            return "Item added Successfully";
        } else {
            Cart_Item cartItem = cartItemRepository.findByItemIdAndCartId(itemId, cart);
            if (cartItem == null) {
                Cart_Item cart_item = new Cart_Item();
                cart_item.setCartId(cart);
                cart_item.setQuantity(quantity);
                cart_item.setItemId(itemId);
                cartItemRepository.save(cart_item);
            } else {
                int oldQuantity = cartItem.getQuantity();
                int newQuantity = oldQuantity + quantity;
                if (newQuantity <= itemDetailsDto.getQuantity()) {

                    cartItem.setQuantity(newQuantity);
                    cartItemRepository.save(cartItem);
                    return "item added successfully";

                }
                throw new NotValidQuantityException("new quantity  exceeds the Stock");

            }
        }
        return null;
    }


    @Override
    public String removeItemToCart(Long itemId ,int userId) {

        if(JwtUtil.userId!=userId){
            return "not a valid user";
        }
        ItemDetailsDto itemDetailsDto=builder.build()
                .get()
                .uri("http://localhost:8085/api/v1/item/verify/{id}", itemId)
                .retrieve()
                .bodyToMono(ItemDetailsDto.class).block();

        if (itemDetailsDto == null){
            throw new EntityNotFoundException("Item Not Found");
        }
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null){
            throw new EntityNotFoundException("Cart Not Found");
        }
        Cart_Item cartItem = cartItemRepository.findByItemIdAndCartId(itemId, cart);
        if (cartItem == null){
            throw new EntityNotFoundException("Item Not Found in the Cart");
        }
        cartItemRepository.delete(cartItem);
        List<Cart_Item> cartItemList = cartItemRepository.findAllByCartId(cart);
        if (cartItemList.isEmpty()){
            cartRepository.delete(cart);
        }
        return "Item removed In the Cart";
    }

}