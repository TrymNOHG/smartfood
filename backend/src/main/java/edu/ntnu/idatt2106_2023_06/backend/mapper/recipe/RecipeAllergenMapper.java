package edu.ntnu.idatt2106_2023_06.backend.mapper.recipe;

import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeAllergenDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeAllergen;

/**

 This class provides a static method for converting a {@link RecipeAllergen} object to a {@link RecipeAllergenDTO} object.
 */
public class RecipeAllergenMapper {

    /**
     * Converts a {@link RecipeAllergen} object to a {@link RecipeAllergenDTO} object.
     *
     * @param recipeAllergen the {@link RecipeAllergen} object to be converted
     * @return a {@link RecipeAllergenDTO} object representing the input {@link RecipeAllergen} object
     */
    public static RecipeAllergenDTO toRecipeAllergenDTO(RecipeAllergen recipeAllergen) {
        return RecipeAllergenDTO
                .builder()
                .displayName(recipeAllergen.getAllergen().getAllergenName())
                .amount(recipeAllergen.getAmount())
                .build();
    }

}
