package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemMoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingItemUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingListLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  A controller class handling shopping items and their operations.
 *
 * @author Trym Hamer Gudvangen
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/item/shopping")
@RequiredArgsConstructor
public class ShoppingItemsController implements IShoppingItemsController{

    private final ItemService itemService;
    private final UserService userService;
    /**
     * The logger for logging information about the operations performed by this controller.
     */
    private final Logger logger = LoggerFactory.getLogger(ShoppingItemsController.class);

    /**
     * Adds items to the shopping list for a given fridge.
     *
     * @param itemDTO     the item to add to the shopping list
     * @param fridgeId    the ID of the fridge for which to add items to the shopping list
     * @param suggestion  whether or not the item was a suggestion
     * @return            a response entity indicating success
     */
    @PostMapping(value="/add")
    @Operation(summary = "Add items to shopping list")
    @Override
    public ResponseEntity<Object> addToShoppingList(@ParameterObject @RequestBody ItemDTO itemDTO,
                                                    @ParameterObject @RequestParam(name = "fridgeId") Long fridgeId,
                                                    @ParameterObject @RequestParam(name = "suggestion") boolean suggestion,
                                                    Authentication authentication){
        authenticate(authentication);

        boolean isSuperUser = userService.isSuperUser(fridgeId, authentication.getName());

        logger.info("Checking whether item is suggestion");
        if(!suggestion && !isSuperUser) {
            logger.info("User is not a superuser and can therefore not add a non-suggestion item");
            throw new UnauthorizedException(authentication.getName(), "Regular user cannot add a non-suggestion item");
        } else {
            itemService.addToShoppingList(itemDTO, fridgeId, suggestion);
            logger.info("New item has been added!");
        }

        return ResponseEntity.ok().build();

    }

    /**
     * An item can be deleted from a shopping list of a fridge given the following conditions:
     * - User is a superuser, then user can delete both suggested and actual items
     * - User is a normal user, then user can delete suggested items but not actual items.
     *
     * @param itemRemoveDTO  the item to remove from the shopping list
     * @param suggestion     whether the item was a suggestion
     * @return               a response entity indicating success
     */
    @DeleteMapping(value="/delete")
    @Operation(summary = "Delete item from shopping list")
    @Override
    public ResponseEntity<Object> deleteItemFromShoppingList(@ParameterObject @RequestBody ItemRemoveDTO itemRemoveDTO,
                                                             @ParameterObject @RequestParam(name = "suggestion") boolean suggestion,
                                                             Authentication authentication){
        authenticate(authentication);

        boolean isSuperUser = userService.isSuperUser(itemRemoveDTO.fridgeId(), authentication.getName());

        logger.info("Checking whether delete is suggestion");
        if(!suggestion && !isSuperUser) {
            logger.info("User is not a superuser and can therefore not delete a non-suggestion item");
            return ResponseEntity.ok().build();
        } else {
            logger.info("User wants to delete item from shopping list");
            itemService.removeItemFromShoppingList(itemRemoveDTO, suggestion);
            logger.info("Items have been deleted!");
        }
        return ResponseEntity.ok().build();
    }


    /**
     * Deletes the items on the shopping list for a given fridge.
     *
     * @param itemDTOList  the list of items to buy
     * @return             a response entity indicating success
     */
    @PostMapping(value="/delete/all", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete items from shopping list")
    @Override
    public ResponseEntity<Object> deleteAllItemsFromShoppingList(@ParameterObject @RequestBody List<ItemRemoveDTO> itemDTOList,
                                                                 Authentication authentication){
        if(itemDTOList.isEmpty()) throw new IllegalArgumentException();
        validateSuperUser(itemDTOList.get(0).fridgeId(), authentication);

        logger.info("User wants to buy item from shopping list");
        itemService.deleteAllItemsFromShoppingList(itemDTOList);
        logger.info("Items have been bought!");
        return ResponseEntity.ok().build();
    }

    /**
     * This method updates a given fridge item to contain the information received.
     * @param shoppingItemUpdateDTO New shopping item information, given as a ShoppingItemUpdateDTO
     * @param authentication        The authentication of the user who sent in the request, given as an Authentication object.
     * @return                      A ResponseEntity indicating the success or failure of the operation.
     */
    @PutMapping(value="/update")
    @Operation(summary = "Update item from shopping list")
    @Override
    public ResponseEntity<Object> updateShoppingListItem(@ParameterObject @RequestBody ShoppingItemUpdateDTO shoppingItemUpdateDTO,
                                                         Authentication authentication) {
        validateSuperUser(shoppingItemUpdateDTO.fridgeId(), authentication);
        logger.info("Attempting to update fridge item");
        itemService.updateShoppingItem(shoppingItemUpdateDTO, authentication.getName());
        logger.info("Successfully updated item");

        return ResponseEntity.ok().build();
    }

    /**
     * Buys the items on the shopping list for a given fridge and places it in fridge items.
     *
     * @param itemMoveDTO  the list of items to buy
     * @return             a response entity indicating success
     */
    @PostMapping(value="/buy", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buy items from shopping list")
    @Override
    public ResponseEntity<Object> buyItemsFromShoppingList(@ParameterObject @RequestBody List<ItemMoveDTO> itemMoveDTO,
                                                           Authentication authentication){

        if(itemMoveDTO.isEmpty()) throw new IllegalArgumentException();

        validateSuperUser(itemMoveDTO.get(0).fridgeId(), authentication);

        logger.info("User wants to buy item from shopping list");
        itemService.buyItemsFromShoppingList(itemMoveDTO);
        logger.info("Items have been bought!");
        return ResponseEntity.ok().build();
    }

    /**
     * Gets the items on the shopping list for a given fridge.
     *
     * @param fridgeId  the ID of the fridge for which to retrieve items from the shopping list
     * @return          a response entity containing the shopping list items
     */
    @GetMapping(value="/get")
    @Operation(summary = "Get items from fridge")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loading items of a given shopping list",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingListLoadDTO.class)) })}
    )
    public ResponseEntity<Object> getShoppingList(@ParameterObject @RequestParam(name = "fridgeId") Long fridgeId){
        logger.info("User wants to get items from shopping list");
        List<ShoppingListLoadDTO> itemList = itemService.getShoppingListItems(fridgeId);
        logger.info("Items have been retrieved!");
        return ResponseEntity.ok(itemList);
    }

    /**
     * Accepts a suggested item on the shopping list for a given fridge.
     *
     * @param itemDTO  the item to accept
     * @return         a response entity indicating success
     */
    @PostMapping(value="/suggestion")
    @Operation(summary = "Accept suggestion in shopping list")
    @Override
    public ResponseEntity<Object> acceptSuggestion(@ParameterObject @RequestBody ItemRemoveDTO itemDTO,
                                                   Authentication authentication){
        validateSuperUser(itemDTO.fridgeId(), authentication);

        logger.info("User wants to accept suggestion");
        itemService.acceptSuggestion(itemDTO);
        logger.info("Suggestion has been accepted");
        return ResponseEntity.ok().build();
    }

    /**
     *  This method authenticates the given authentication object by checking if it is null or not authenticated.
     *  If the authentication is null or not authenticated, an UnauthorizedException is thrown.
     *  @param authentication           The authentication object to authenticate
     *  @throws UnauthorizedException   if the authentication is null or not authenticated
     */
    private void authenticate(Authentication authentication){
        if(authentication == null || !authentication.isAuthenticated()) throw new UnauthorizedException("Anon");
    }

    /**
     * This method validates whether the authenticated user is a superuser for the given fridge ID.
     * The method first authenticates the user using the provided authentication object.
     * It then checks if the authenticated user is a superuser for the given fridge ID using the userService.
     * If the user is not a superuser, an UnauthorizedException is thrown.
     *
     * @param fridgeId the ID of the fridge to check for superuser access
     * @param authentication the authentication object of the user
     * @throws UnauthorizedException if the authentication is null or not authenticated,
     *                              or if the authenticated user is not a superuser for the given fridge ID
     */
    private void validateSuperUser(Long fridgeId, Authentication authentication) {
        authenticate(authentication);

        boolean isSuperUser = userService.isSuperUser(fridgeId, authentication.getName());
        if(!isSuperUser) throw new UnauthorizedException(authentication.getName(), "User must be super user");
    }

}
