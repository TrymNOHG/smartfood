package edu.ntnu.idatt2106_2023_06.backend.controller;


import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**

 This class represents the REST controller for managing items in a fridge and shopping list.
 It handles requests related to adding, retrieving, and deleting items from a fridge and a shopping list.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    /**
     * The logger for logging information about the operations performed by this controller.
     */
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    /**
     * Adds an item to the fridge.
     *
     * @param itemDTO The item to add to the fridge.
     * @param fridgeId The id of the fridge to add the item to.
     * @return A ResponseEntity indicating the success or failure of the operation.
     */
    @PostMapping(value="/fridge/add")
    @Operation(summary = "Add items to fridge")
    public ResponseEntity<Object> addToFridge(@ParameterObject @RequestBody ItemDTO itemDTO,
                                              @ParameterObject @RequestParam(name = "fridgeId") Long fridgeId){

        logger.info("item to add: " + itemDTO);
        logger.info("fridge to be added in: " + fridgeId);
        logger.info("User wants to add a new items to fridge");
        Long itemId = itemService.addItem(itemDTO);
        itemService.addToFridge(itemId, fridgeId, itemDTO.quantity());
        logger.info("New items has been added!");
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves items from a fridge.
     *
     * @param fridgeId The id of the fridge to retrieve items from.
     * @return A ResponseEntity containing the retrieved items, or indicating a failure if appropriate.
     */
    @GetMapping(value="/fridge/get")
    @Operation(summary = "Get items from fridge")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loading items of a given fridge",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class)) })}
    )
    public ResponseEntity<Object> getFridge(@ParameterObject @RequestParam(name = "fridgeId") Long fridgeId){
        logger.info("User wants to get items from fridge");
        List<ItemDTO> itemList = itemService.getFridgeItems(fridgeId);
        logger.info("Items have been retrieved!");
        return ResponseEntity.ok(itemList);
    }

    /**
     * Removes an item from a fridge.
     *
     * @param itemRemoveDTO The item to remove from the fridge.
     * @return A ResponseEntity indicating the success or failure of the operation.
     */
    @DeleteMapping(value="/fridge/delete")
    @Operation(summary = "Delete item from fridge")
    public ResponseEntity<Object> deleteItemFromFridge(@ParameterObject @RequestBody ItemRemoveDTO itemRemoveDTO){
        logger.info("User wants to delete item from fridge");
        itemService.deleteItemFromFridge(itemRemoveDTO);
        logger.info("Items have been removed!");
        return ResponseEntity.ok().build();
    }

    /**
     * Adds items to the shopping list for a given fridge.
     *
     * @param itemDTO     the item to add to the shopping list
     * @param fridgeId    the ID of the fridge for which to add items to the shopping list
     * @param suggestion  whether or not the item was a suggestion
     * @return            a response entity indicating success
     */
    @PostMapping(value="/shopping/add")
    @Operation(summary = "Add items to shopping list")
    public ResponseEntity<Object> addToShoppingList(@ParameterObject @RequestBody ItemDTO itemDTO,
                                                    @ParameterObject @RequestParam(name = "fridgeId") Long fridgeId,
                                                    @ParameterObject @RequestParam(name = "suggestion") boolean suggestion){

        logger.info("User wants to add a new items to shopping list");
        Long itemId = itemService.addItem(itemDTO);
        itemService.addToShoppingList(itemId, fridgeId, itemDTO.quantity(), suggestion);
        logger.info("New items has been added!");
        return ResponseEntity.ok().build();
    }

    /**
     * Gets the items on the shopping list for a given fridge.
     *
     * @param fridgeId  the ID of the fridge for which to retrieve items from the shopping list
     * @return          a response entity containing the shopping list items
     */
    @GetMapping(value="/shopping/get")
    @Operation(summary = "Get items from fridge")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loading items of a given shopping list",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class)) })}
    )
    public ResponseEntity<Object> getShoppingList(@ParameterObject @RequestParam(name = "fridgeId") Long fridgeId){
        logger.info("User wants to get items from shopping list");
        List<ItemDTO> itemList = itemService.getShoppingListItems(fridgeId);
        logger.info("Items have been retrieved!");
        return ResponseEntity.ok(itemList);
    }

    /**
     * Deletes an item from the shopping list for a given fridge.
     *
     * @param itemRemoveDTO  the item to remove from the shopping list
     * @param suggestion     whether or not the item was a suggestion
     * @return               a response entity indicating success
     */
        @DeleteMapping(value="/shopping/delete")
        @Operation(summary = "Delete item from shopping list")
        public ResponseEntity<Object> deleteItemFromShoppingList(@ParameterObject @RequestBody ItemRemoveDTO itemRemoveDTO,
                                                                 @ParameterObject @RequestParam(name = "suggestion") boolean suggestion){
            logger.info("User wants to delete item from shopping list");
            itemService.deleteItemFromShoppingList(itemRemoveDTO, suggestion);
            logger.info("Items have been deleted!");
            return ResponseEntity.ok().build();
        }

    /**
     * Buys the items on the shopping list for a given fridge.
     *
     * @param itemDTOList  the list of items to buy
     * @return             a response entity indicating success
     */
    @PostMapping(value="/shopping/buy", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buy items from shopping list")
    public ResponseEntity<Object> buyItemsFromShoppingList(@ParameterObject @RequestBody List<ItemRemoveDTO> itemDTOList){
        logger.info("User wants to buy item from shopping list");
        itemService.buyItemsFromShoppingList(itemDTOList);
        logger.info("Items have been bought!");
        return ResponseEntity.ok().build();
    }

    /**
     * Accepts a suggested item on the shopping list for a given fridge.
     *
     * @param itemDTO  the item to accept
     * @return         a response entity indicating success
     */
    @PostMapping(value="/shopping/suggestion")
    @Operation(summary = "Accept suggestion in shopping list")
    public ResponseEntity<Object> acceptSuggestion(@ParameterObject @RequestBody ItemRemoveDTO itemDTO){
        logger.info("User wants to accept suggestion");
        itemService.acceptSuggestion(itemDTO);
        logger.info("Suggestion has been accepted");
        return ResponseEntity.ok().build();
    }
}
