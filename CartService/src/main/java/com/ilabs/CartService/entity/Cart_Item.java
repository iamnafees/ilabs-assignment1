package com.ilabs.CartService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart_Item {

    //    private static final long serialVersionUID = 4378639109849248320L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartItemId;
    private long itemId;
    private int quantity;
    @ManyToOne
    private Cart cartId;


}