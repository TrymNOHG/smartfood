package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

/**
 * This record represents a DTO for a part of the ingredient list.
 * @param partName      The name of the part
 * @param ingredients   The list of ingredients
 */
@Builder
public record RecipePartDTO(String partName, @NonNull List<RecipeItemDTO> ingredients) {
}
