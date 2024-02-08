package com.ilabs.CartService.service;

public interface CartService {
    String addItemToCart(Long itemId, int userId, int quantity);

    String removeItemToCart(Long itemId, int userId);
}