package edu.ntnu.idatt2106_2023_06.backend.dto.stat;

public record StatAddItemToFridgeDTO(
        Double price,
        Integer quantity,
        String itemName,
        String storeName,
        Long fridgeId) {
}
