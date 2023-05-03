package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record RecipeSuggestionAddDTO(@NonNull Long recipeId, @NonNull Long fridgeId, @NonNull Long userId) {
}
