package edu.ntnu.idatt2106_2023_06.backend.dto.items;

public record ItemRemoveDTO(String itemName, String store, Long fridgeId, int quantity) {
}
