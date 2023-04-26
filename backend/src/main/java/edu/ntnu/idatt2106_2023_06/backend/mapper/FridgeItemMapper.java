package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;

import java.util.Date;

public class FridgeItemMapper {

    public static FridgeItems toFridgeItems(ShoppingItems shoppingItems) {
        return FridgeItems
                .builder()
                .id(new FridgeItemsId(shoppingItems.getItem().getItemId(), shoppingItems.getFridge().getFridgeId()))
                .item(shoppingItems.getItem())
                .fridge(shoppingItems.getFridge())
                .purchaseDate(new Date())
                .expirationDate(new Date()) //TODO: change to include expiration date
                .quantity(0)
                .build();
    }

}
