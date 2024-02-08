package com.ilabs.CartService.repository;

import com.ilabs.CartService.entity.Cart;
import com.ilabs.CartService.entity.Cart_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<Cart_Item, Long> {

    List<Cart_Item> findAllByCartId(Cart cart);


    Cart_Item findByItemIdAndCartId(Long itemId, Cart cartId);
}