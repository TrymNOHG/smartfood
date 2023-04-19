package edu.ntnu.idatt2106_2023_06.backend.controller;


import edu.ntnu.idatt2106_2023_06.backend.dto.ItemDTO;
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
        itemService.addItem(itemDTO);
        itemService.addToFridge(itemDTO.name(), fridgeId);
        logger.info("New items has been added!");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/get/fridge")
    @Operation(summary = "Get items from fridge")
    public ResponseEntity<Object> getFridge(@ParameterObject @RequestParam(name = "fridgeId") Long fridgeId){
        logger.info("User wants to add a new items to fridge");
        List<ItemDTO> itemList = itemService.getFridgeItems(fridgeId);
        logger.info("New items has been added!");
        return ResponseEntity.ok(itemList);
    }





}
