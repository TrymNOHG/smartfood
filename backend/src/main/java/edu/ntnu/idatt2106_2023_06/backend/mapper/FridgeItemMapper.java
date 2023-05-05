package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;

import java.time.LocalDateTime;

/**
 * This class provides methods for mapping between different types of Fridge items DTOs and Fridge items models.
 */
public class FridgeItemMapper {

    /**
     * Converts a ShoppingItems object into a FridgeItems object.
     *
     * @param shoppingItems The ShoppingItems object to convert.
     * @return The resulting FridgeItems object.
     */
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

    /**
     * Converts a FridgeItems object into a FridgeItemLoadDTO object.
     *
     * @param fridgeItem The FridgeItems object to convert.
     * @return The resulting FridgeItemLoadDTO object.
     */
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
