package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Amount;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record RecipeAllergenDTO(@NonNull String displayName, @NonNull Amount amount) {
}
