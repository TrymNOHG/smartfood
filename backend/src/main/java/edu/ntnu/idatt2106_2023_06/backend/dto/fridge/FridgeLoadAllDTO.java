package edu.ntnu.idatt2106_2023_06.backend.dto.fridge;

import lombok.NonNull;

import java.util.List;

/**
 * This record represents a list of fridge loading DTO.
 * @param fridgeDTOS The list of fridges
 */
public record FridgeLoadAllDTO(@NonNull List<FridgeDTO> fridgeDTOS) {
}
