package com.ilabs.CartService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

    //    private static final long serialVersionUID = 4378639109849248320L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartId;

    private long userId;

}