package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

//TODO: make sure instructions and parts arrive in the correct order.
@Builder
public record RecipeLoadDTO(@NonNull String recipeName, String description,
        String author, int servingSize, int difficulty,
        String thumbnail, double cookingTime, int numMatchingItems,
        @NonNull List<InstructionDTO> instructions,
        @NonNull List<RecipePartDTO> recipeParts,
        @NonNull List<RecipeAllergenDTO> allergens) {
}
