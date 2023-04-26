package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemMoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingItemUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IShoppingItemsController {


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
    ResponseEntity<Object> addToShoppingList(@ParameterObject @RequestBody ItemDTO itemDTO,
                                             @ParameterObject @RequestParam(name = "fridgeId") Long fridgeId,
                                             @ParameterObject @RequestParam(name = "suggestion") boolean suggestion,
                                             Authentication authentication);

    /**
     * An item can be deleted from a shopping list of a fridge given the following conditions:
     * - User is a superuser, then user can delete both suggested and actual items
     * - User is a normal user, then user can delete suggested items but not actual items.
     *
     * @param itemRemoveDTO  the item to remove from the shopping list
     * @param suggestion     whether the item was a suggestion
     * @return               a response entity indicating success
     */
    @DeleteMapping(value="/shopping/delete")
    @Operation(summary = "Delete item from shopping list")
    ResponseEntity<Object> deleteItemFromShoppingList(@ParameterObject @RequestBody ItemRemoveDTO itemRemoveDTO,
                                                      @ParameterObject @RequestParam(name = "suggestion") boolean suggestion,
                                                      Authentication authentication);

    /**
     * Deletes the items on the shopping list for a given fridge.
     *
     * @param itemDTOList  the list of items to buy
     * @return             a response entity indicating success
     */
    @PostMapping(value="/shopping/delete/all", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete items from shopping list")
    ResponseEntity<Object> deleteAllItemsFromShoppingList(@ParameterObject @RequestBody List<ItemRemoveDTO> itemDTOList,
                                                          Authentication authentication);

    @PutMapping(value="/shopping/update")
    @Operation(summary = "Update item from shopping list")
    ResponseEntity<Object> updateShoppingListItem(@ParameterObject @RequestBody ShoppingItemUpdateDTO shoppingItemUpdateDTO,
                                                  Authentication authentication);

    /**
     * Buys the items on the shopping list for a given fridge and places it in fridge items.
     *
     * @param itemDTOList  the list of items to buy
     * @return             a response entity indicating success
     */
    @PostMapping(value="/shopping/buy", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buy items from shopping list")
    ResponseEntity<Object> buyItemsFromShoppingList(@ParameterObject @RequestBody List<ItemMoveDTO> itemDTOList,
                                                    Authentication authentication);

    /**
     * Accepts a suggested item on the shopping list for a given fridge.
     *
     * @param itemDTO  the item to accept
     * @return         a response entity indicating success
     */
    @PostMapping(value="/shopping/suggestion")
    @Operation(summary = "Accept suggestion in shopping list")
    ResponseEntity<Object> acceptSuggestion(@ParameterObject @RequestBody ItemRemoveDTO itemDTO,
                                            Authentication authentication);

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
    ResponseEntity<Object> getShoppingList(@ParameterObject @RequestParam(name = "fridgeId") Long fridgeId);


}
