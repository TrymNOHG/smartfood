package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record RecipeSuggestionLoad(@NonNull RecipeLoadDTO recipeLoadDTO, @NonNull Long UserId) {
}
