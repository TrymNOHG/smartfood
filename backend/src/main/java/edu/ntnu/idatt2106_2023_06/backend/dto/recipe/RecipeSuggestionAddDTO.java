package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.Builder;
import lombok.NonNull;

/**
 * This record represents a DTO for a suggestion of a recipe
 * @param recipeId  The ID of the recipe
 * @param fridgeId  The ID of the fridge the suggestion is for
 * @param userId    The ID of the user that is suggesting
 */
@Builder
public record RecipeSuggestionAddDTO(@NonNull Long recipeId, @NonNull Long fridgeId, @NonNull Long userId) {
}
