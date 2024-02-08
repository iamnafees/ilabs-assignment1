package com.ilab.ItemService.service;

import com.ilab.ItemService.dto.ItemEditRequestDto;
import com.ilab.ItemService.dto.ItemRequestDto;
import com.ilab.ItemService.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    String addItem(ItemRequestDto itemRequestDto);

    Item viewItemById(Long id);

    List<Item> viewAllItem();

    Item updateItem(ItemEditRequestDto itemEditRequestDto, Long id);

    String deleteItem(Long id);

    Item verifyItemById(Long id);
}