package edu.ntnu.idatt2106_2023_06.backend.mapper.recipe;

import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipePartDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipePart;

import java.util.stream.Collectors;

/**

 Mapper class for mapping RecipePart objects to RecipePartDTO objects.
 */
public class RecipePartMapper {

    /**
     * Converts a {@link RecipePart} object to a {@link RecipePartDTO} object.
     *
     * @param recipePart the {@link RecipePart} object to convert
     * @return a new {@link RecipePartDTO} object with the same values as the input {@link RecipePart} object
     */
    public static RecipePartDTO toRecipePartDTO(RecipePart recipePart) {
        return RecipePartDTO
                .builder()
                .partName(recipePart.getPartName())
                .ingredients(recipePart.getItemsInRecipe()
                        .stream()
                        .map(RecipeItemMapper::toRecipeItemDTO)
                        .collect(Collectors.toList()))
                .build();
    }

}
