package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingListLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;

/**
 The ShoppingItemMapper class contains methods for mapping between FridgeItems and ShoppingItems model, and
 between Item and ShoppingListLoadDTO DTO.
 */
public class ShoppingItemMapper {

    /**
     * This method converts a FridgeItems object to a ShoppingItems object.
     * @param fridgeItems FridgeItems object to be converted.
     * @return ShoppingItems object.
     */
    public static ShoppingItems toShoppingItem(FridgeItems fridgeItems) {
        return ShoppingItems
                .builder()
                .item(fridgeItems.getItem())
                .quantity(0)
                .fridge(fridgeItems.getFridge())
                .suggestion(false) //TODO: can change
                .build();
    }

    /**
     * This method converts an Item object to a ShoppingListLoadDTO object.
     * @param item Item object to be converted.
     * @param quantity The quantity of the item.
     * @param suggestion Boolean value representing whether the item is a suggestion or not.
     * @return ShoppingListLoadDTO object.
     */
    public static ShoppingListLoadDTO toShoppingListLoadDTO(Item item, int quantity, boolean suggestion) {
        return ShoppingListLoadDTO
                .builder()
                .itemId(item.getItemId())
                .name(item.getProductName())
                .store(item.getStore().getStoreName())
                .price(item.getPrice())
                .description(item.getDesc())
                .image(item.getPictureLink())
                .quantity(quantity)
                .suggestion(suggestion)
                .ean(item.getEan())
                .build();
    }

}
