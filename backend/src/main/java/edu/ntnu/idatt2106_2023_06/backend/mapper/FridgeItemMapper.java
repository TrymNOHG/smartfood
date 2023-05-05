package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;

import java.time.LocalDateTime;

public class FridgeItemMapper {

    public static FridgeItems toFridgeItems(ShoppingItems shoppingItems) {
        return FridgeItems
                .builder()
                .id(new FridgeItemsId(shoppingItems.getItem().getItemId(), shoppingItems.getFridge().getFridgeId()))
                .item(shoppingItems.getItem())
                .fridge(shoppingItems.getFridge())
                .purchaseDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(shoppingItems.getItem().getExpiresIn())) //TODO: change to include expiration date
                .amount(0)
                .build();
    }

    public static FridgeItemLoadDTO toFridgeItemLoadDTO(FridgeItems fridgeItem) {
        Item item = fridgeItem.getItem();
        return FridgeItemLoadDTO
                .builder()
                .itemId(item.getItemId())
                .price(item.getPrice())
                .image(item.getPictureLink())
                .name(item.getProductName())
                .store(item.getStore().getStoreName())
                .description(item.getDesc())
                .ean(item.getEan())
                .purchaseDate(fridgeItem.getPurchaseDate())
                .expirationDate(fridgeItem.getExpirationDate()) //TODO: change to include expiration date
                .amount(fridgeItem.getAmount())
                .unit(fridgeItem.getItem().getUnit())
                .build();
    }

}
