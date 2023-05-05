package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Amount;
import lombok.Builder;
import lombok.NonNull;

/**
 * This record represents a DTO for a recipe allergen.
 * @param displayName   The name of the allergen.
 * @param amount        The status of how much the allergen is present in the recipe.
 */
@Builder
public record RecipeAllergenDTO(@NonNull String displayName, @NonNull Amount amount) {
}
