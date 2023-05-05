package edu.ntnu.idatt2106_2023_06.backend.dto.stat;

/**
 * This record represents a DTO for the statistic of deleting from a fridge.
 */
public record StatDeleteFromFridgeDTO(
        Integer amountDeleted,
        String itemName,
        String storeName,
        Long fridgeId) {
}
