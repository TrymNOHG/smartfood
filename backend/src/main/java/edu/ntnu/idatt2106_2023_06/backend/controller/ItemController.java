package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


/**

 This class represents the REST controller for managing items in a fridge and shopping list.
 It handles requests related to adding, retrieving, and deleting items from a fridge and a shopping list.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController implements IItemController{

    /**
     * The item service necessary for operating on items stored in the database.
     */
    private final ItemService itemService;

    /**
     * The logger for logging information about the operations performed by this controller.
     */
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

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
    public ResponseEntity<Object> addUnitToExistingItems(){
        logger.info("Parsing the items");
        itemService.addUnitToExistingItems();
        logger.info("All items now have amount and unit");
        return ResponseEntity.ok().build();
    }

}
