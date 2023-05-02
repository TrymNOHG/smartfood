package edu.ntnu.idatt2106_2023_06.backend.mapper.recipe;

import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipePartDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipePart;

import java.util.stream.Collectors;

public class RecipePartMapper {

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
