package com.ilab.ItemService.controller;

import com.ilab.ItemService.constants.ComConstants;
import com.ilab.ItemService.dto.ItemEditRequestDto;
import com.ilab.ItemService.dto.ItemRequestDto;
import com.ilab.ItemService.entity.Item;
import com.ilab.ItemService.service.ItemService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("addItem")
   @RolesAllowed({ComConstants.ROLE_ADMIN})
    public ResponseEntity<String> addItem (@RequestBody ItemRequestDto itemRequestDto){
        return new ResponseEntity<>(itemService.addItem(itemRequestDto), HttpStatus.CREATED) ;
    }

    @GetMapping("view/{id}")
   @RolesAllowed({ComConstants.ROLE_ADMIN,ComConstants.ROLE_CUSTOMER})
    public ResponseEntity<Item>  viewItemById(@PathVariable Long id){
        return new ResponseEntity<>(itemService.viewItemById(id),HttpStatus.FOUND);
    }

    @GetMapping("viewall")
   @RolesAllowed({ComConstants.ROLE_ADMIN,ComConstants.ROLE_CUSTOMER})
    public ResponseEntity<List<Item>> viewAllItem(){
        return new ResponseEntity<>(itemService.viewAllItem(),HttpStatus.FOUND);
    }

    @PutMapping("update/{id}")
   @RolesAllowed({ComConstants.ROLE_ADMIN})
    public ResponseEntity<Item> editItem (@RequestBody ItemEditRequestDto itemEditRequestDto, @PathVariable Long id ){
        return new ResponseEntity<>(itemService.updateItem(itemEditRequestDto,id),HttpStatus.FOUND);
    }

    @DeleteMapping("delete")
    @RolesAllowed({ComConstants.ROLE_ADMIN})
    public ResponseEntity<String> deleteItem(@RequestParam Long id){
        return new ResponseEntity<>(itemService.deleteItem(id),HttpStatus.OK);
    }

    @GetMapping("verify/{id}")
    public ResponseEntity<Item> verifyItemById(@PathVariable Long id ){
        return new ResponseEntity<>(itemService.verifyItemById(id),HttpStatus.OK);

    }


}
