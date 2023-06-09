package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.*;

import java.util.List;

/**
 * This record represents the DTO used for loading a whole recipe.
 */
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class RecipeLoadDTO{
    @NonNull private Long recipeId;
    @NonNull private String recipeName;
    private String description, author, thumbnail;
    private int servingSize, difficulty, numMatchingItems;
    private double cookingTime;
    @NonNull private List<InstructionDTO> instructions;
    @NonNull private List<RecipePartDTO> recipeParts;
    @NonNull private List<RecipeAllergenDTO> allergens;

}
