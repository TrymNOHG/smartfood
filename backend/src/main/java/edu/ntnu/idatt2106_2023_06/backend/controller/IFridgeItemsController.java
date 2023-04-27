package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

public interface IFridgeItemsController {

    /**
     * Adds an item to the fridge.
     *
     * @param itemDTO The item to add to the fridge.
     * @param fridgeId The id of the fridge to add the item to.
     * @return A ResponseEntity indicating the success or failure of the operation.
     */
    @PostMapping(value="/fridge/add")
    @Operation(summary = "Add items to fridge")
    ResponseEntity<Object> addToFridge(@ParameterObject @RequestBody ItemDTO itemDTO,
                                       @ParameterObject @RequestParam(name = "fridgeId") Long fridgeId);

    /**
     * Removes an item from a fridge.
     *
     * @param itemRemoveDTO The item to remove from the fridge.
     * @return A ResponseEntity indicating the success or failure of the operation.
     */
    @DeleteMapping(value="/fridge/delete")
    @Operation(summary = "Delete item from fridge")
    ResponseEntity<Object> deleteItemFromFridge(@ParameterObject @RequestBody ItemRemoveDTO itemRemoveDTO);

    @PutMapping(value="/fridge/update")
    @Operation(summary = "Update item in fridge")
    ResponseEntity<Object> updateFridgeItem(@ParameterObject @RequestBody FridgeItemUpdateDTO fridgeItemUpdateDTO,
                                            Authentication authentication);

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
    ResponseEntity<Object> getFridge(@ParameterObject @RequestParam(name = "fridgeId") Long fridgeId);


}
