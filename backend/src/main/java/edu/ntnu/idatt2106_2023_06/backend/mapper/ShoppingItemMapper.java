package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingListLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;

public class ShoppingItemMapper {

    public static ShoppingItems toShoppingItem(FridgeItems fridgeItems) {
        return ShoppingItems
                .builder()
                .item(fridgeItems.getItem())
                .quantity(0)
                .fridge(fridgeItems.getFridge())
                .suggestion(false) //TODO: can change
                .build();
    }

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
