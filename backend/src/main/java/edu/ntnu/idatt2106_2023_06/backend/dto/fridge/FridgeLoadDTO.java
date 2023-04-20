package edu.ntnu.idatt2106_2023_06.backend.dto.fridge;

import lombok.NonNull;

/**
 * This record represents a fridge loading DTO.
 * @param fridgeId      The id of the fridge, given as a Long.
 * @param fridgeName    The name of the fridge, given as a String.
 */
public record FridgeLoadDTO(@NonNull Long fridgeId, @NonNull String fridgeName) {
}
