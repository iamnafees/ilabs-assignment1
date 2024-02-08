package com.ilab.ItemService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {

    private String itemName;
    private String itemDescription;
    private int quantity;
    private double price;
}
