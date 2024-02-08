package com.ilabs.CartService.controller;

import com.ilabs.CartService.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.security.RolesAllowed;
import com.ilabs.CartService.constants.ComConstants;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("add")
    @RolesAllowed({ComConstants.ROLE_ADMIN, ComConstants.ROLE_CUSTOMER})
    public ResponseEntity<String> addToCart(@RequestParam Long itemId, @RequestParam int userId, @RequestParam int quantity) {
        return new ResponseEntity<>(cartService.addItemToCart(itemId, userId, quantity), HttpStatus.CREATED);
    }

    @DeleteMapping("remove")
    @RolesAllowed({ComConstants.ROLE_ADMIN, ComConstants.ROLE_CUSTOMER})
    public ResponseEntity<String> removeItemToCart(@RequestParam Long itemId, @RequestParam int userId) {
        return new ResponseEntity<>(cartService.removeItemToCart(itemId, userId), HttpStatus.OK);
    }

}

