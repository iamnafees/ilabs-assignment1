package com.ilabs.CartService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailsDto {


    private Long itemId;
    private String itemName;
    private String itemDescription;
    private int quantity;
}