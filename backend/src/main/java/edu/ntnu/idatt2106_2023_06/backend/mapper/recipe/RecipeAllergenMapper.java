package edu.ntnu.idatt2106_2023_06.backend.mapper.recipe;

import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeAllergenDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeAllergen;

public class RecipeAllergenMapper {

    public static RecipeAllergenDTO toRecipeAllergenDTO(RecipeAllergen recipeAllergen) {
        return RecipeAllergenDTO
                .builder()
                .displayName(recipeAllergen.getAllergen().getAllergenName())
                .amount(recipeAllergen.getAmount())
                .build();
    }

}
