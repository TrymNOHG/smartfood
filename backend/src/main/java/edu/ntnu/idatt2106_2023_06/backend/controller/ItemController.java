package edu.ntnu.idatt2106_2023_06.backend.controller;


import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @PostMapping(value="/add/fridge")
    @Operation(summary = "Add items to fridge")
    public ResponseEntity<Object> addToFridge(@ParameterObject @RequestBody ItemDTO itemDTO,
                                              @ParameterObject @RequestParam(name = "fridgeId") Long fridgeId){

        logger.info("User wants to add a new items to fridge");
        Long itemId = itemService.addItem(itemDTO);
        itemService.addToFridge(itemId, fridgeId, itemDTO.quantity());
        logger.info("New items has been added!");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/get/fridge")
    @Operation(summary = "Get items from fridge")
    public ResponseEntity<Object> getFridge(@ParameterObject @RequestParam(name = "fridgeId") Long fridgeId){
        logger.info("User wants to get items from fridge");
        List<ItemDTO> itemList = itemService.getFridgeItems(fridgeId);
        logger.info("Items have been retrieved!");
        return ResponseEntity.ok(itemList);
    }

    @PostMapping(value="/add/shopping")
    @Operation(summary = "Add items to shopping list")
    public ResponseEntity<Object> addToShoppingList(@ParameterObject @RequestBody ItemDTO itemDTO,
                                                    @ParameterObject @RequestParam(name = "fridgeId") Long fridgeId){

        logger.info("User wants to add a new items to shopping list");
        Long itemId = itemService.addItem(itemDTO);
        itemService.addToShoppingList(itemId, fridgeId, itemDTO.quantity());
        logger.info("New items has been added!");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/get/shopping")
    @Operation(summary = "Get items from fridge")
    public ResponseEntity<Object> getShoppingList(@ParameterObject @RequestParam(name = "fridgeId") Long fridgeId){
        logger.info("User wants to get items from shopping list");
        List<ItemDTO> itemList = itemService.getShoppingListItems(fridgeId);
        logger.info("Items have been retrieved!");
        return ResponseEntity.ok(itemList);
    }

    @DeleteMapping(value="/delete/shopping")
    @Operation(summary = "Delete item from shopping list")
    public ResponseEntity<Object> deleteItemFromShoppingList(@ParameterObject @RequestBody ItemRemoveDTO itemRemoveDTO){
        logger.info("User wants to delete item from shopping list");
        itemService.deleteItemFromShoppingList(itemRemoveDTO);
        logger.info("Items have been deleted!");
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/buy/shopping")
    @Operation(summary = "Buy items from shopping list")
    public ResponseEntity<Object> buyItemsFromShoppingList(@ParameterObject @RequestBody List<ItemRemoveDTO> itemDTOList){
        logger.info("User wants to buy item from shopping list");
        itemService.buyItemsFromShoppingList(itemDTOList);
        logger.info("Items have been bought!");
        return ResponseEntity.ok().build();
    }





}
