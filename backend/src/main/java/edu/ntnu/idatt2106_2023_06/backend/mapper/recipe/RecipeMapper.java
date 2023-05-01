package edu.ntnu.idatt2106_2023_06.backend.mapper.recipe;

import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;

import java.util.stream.Collectors;

public class RecipeMapper {

    public static RecipeLoadDTO toRecipeLoadDTO(Recipe recipe) {
        return RecipeLoadDTO
                .builder()
                .recipeName(recipe.getRecipeName())
                .description(recipe.getDescription())
                .author(recipe.getAuthor())
                .difficulty(recipe.getDifficulty())
                .servingSize(recipe.getServingSize())
                .thumbnail(recipe.getThumbnailLink())
                .cookingTime(recipe.getCookTime())
                .instructions(recipe.getInstructions()
                        .stream()
                        .map(InstructionMapper::toInstructionDTO)
                        .collect(Collectors.toList()))
                .recipeParts(recipe.getRecipeParts()
                        .stream()
                        .map(RecipePartMapper::toRecipePartDTO)
                        .collect(Collectors.toList()))
                .allergens(recipe.getRecipeAllergenSet()
                        .stream()
                        .map(RecipeAllergenMapper::toRecipeAllergenDTO)
                        .collect(Collectors.toList()))
                .build();
    }

}
