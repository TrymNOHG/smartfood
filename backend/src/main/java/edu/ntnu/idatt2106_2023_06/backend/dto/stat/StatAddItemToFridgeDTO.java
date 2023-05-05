package edu.ntnu.idatt2106_2023_06.backend.dto.stat;

import lombok.Builder;

/**
 * This record represents a dto for adding a statistic.
 * @param price     The price of the item
 * @param quantity  The amount of the item
 * @param itemName  The name of the item
 * @param storeName The name of the store the item comes from
 * @param fridgeId  The fridge this statistic is relevant for
 */
@Builder
public record StatAddItemToFridgeDTO(
        Double price,
        Integer quantity,
        String itemName,
        String storeName,
        Long fridgeId) {
}
