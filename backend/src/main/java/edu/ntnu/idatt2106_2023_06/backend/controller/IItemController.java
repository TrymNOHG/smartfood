package edu.ntnu.idatt2106_2023_06.backend.controller;


import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This interface contains the methods needed in the ItemController.
 */
public interface IItemController {

    /**
     * This method adds amount and unit to existing items.
     *
     * @return ResponseEntity indicating whether the operation was successful or not.
     */
    @PostMapping(value="/addUnits")
    @Operation(summary = "Add amount and unit to existing items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add amount and unit to existing items",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Recipe.class)) })}
    )
    ResponseEntity<Object> addUnitToExistingItems();
}
