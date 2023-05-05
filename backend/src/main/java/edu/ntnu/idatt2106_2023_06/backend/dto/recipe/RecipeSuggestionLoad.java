package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.Builder;
import lombok.NonNull;

/**
 * This record represents a DTO for loading a recipe suggestion.
 * @param recipeLoadDTO The information of the suggestion.
 * @param UserId        The userId of the suggester.
 */
@Builder
public record RecipeSuggestionLoad(@NonNull RecipeLoadDTO recipeLoadDTO, @NonNull Long UserId) {
}
