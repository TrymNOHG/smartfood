package edu.ntnu.idatt2106_2023_06.backend.dto.fridge;

import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import lombok.NonNull;

import java.util.List;

/**
 * This record represents a fridge loading DTO.
 * @param fridgeList The list of fridges
 */
public record FridgeLoadDTO(@NonNull List<Fridge> fridgeList) {
}
