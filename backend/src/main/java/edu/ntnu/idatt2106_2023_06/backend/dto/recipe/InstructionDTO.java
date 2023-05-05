package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.Builder;
import lombok.NonNull;

/**
 * This record represents a DTO for a recipe instruction.
 * @param instruction   The actual instruction, given as a String.
 * @param imageLink     The link to the picture of the instruction (if it exists).
 */
@Builder
public record InstructionDTO(@NonNull String instruction, String imageLink) {
}
