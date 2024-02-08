package com.ilab.ItemService.service.impl;

import com.ilab.ItemService.dto.ItemEditRequestDto;
import com.ilab.ItemService.dto.ItemRequestDto;
import com.ilab.ItemService.entity.Item;
import com.ilab.ItemService.exception.DataNotFoundException;
import com.ilab.ItemService.exception.NotValidQuantityException;
import com.ilab.ItemService.repository.ItemRepository;
import com.ilab.ItemService.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {


    private final ItemRepository itemRepository;
    @Override
    public String addItem(ItemRequestDto itemRequestDto) {
        Item item = new Item();
        item.setItemName(itemRequestDto.getItemName());
        item.setItemDescription(itemRequestDto.getItemDescription());
        item.setQuantity(itemRequestDto.getQuantity());
        item.setPrice(itemRequestDto.getPrice());
        itemRepository.save(item);

        return "Saved Successfully";
    }

    @Override
    public Item viewItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with Id"+ id));
    }

    @Override
    public List<Item> viewAllItem() throws DataNotFoundException {
        List<Item> itemList =  itemRepository.findAll();
        if (itemList.isEmpty()){
            throw new DataNotFoundException("No Items Found");
        }
        return itemList;
    }

    @Override
    public Item updateItem(ItemEditRequestDto itemEditRequestDto, Long id) {

        Item item =  itemRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No Item Found in this Id"+id));
        if(itemEditRequestDto.getQuantity()<0){
            throw new NotValidQuantityException();
        }


        item.setQuantity(itemEditRequestDto.getQuantity()+item.getQuantity());

        itemRepository.save(item);
        return item;
    }
    @Override
    public String deleteItem(Long id) {

        Item item =   itemRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No Item Found in this Id"+id));
        itemRepository.delete(item);
        return "Deleted Successfully";
    }

    @Override
    public Item verifyItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent())
        {
            return item.get();
        }
        return null;

    }
}
