package edu.ntnu.idatt2106_2023_06.backend.mapper.recipe;

import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeSuggestionLoad;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;

import java.util.stream.Collectors;

/**
 Mapper class for the {@link Recipe} entity and its DTOs.
 */
public class RecipeMapper {

    /**
     * Maps a Recipe object to a RecipeLoadDTO object.
     * @param recipe The Recipe object to be mapped.
     * @return A RecipeLoadDTO object.
     */
    public static RecipeLoadDTO toRecipeLoadDTO(Recipe recipe) {
        return toRecipeLoadDTO(recipe, 0);
    }

    /**
     * Maps a Recipe object to a RecipeLoadDTO object.
     * @param recipe The Recipe object to be mapped.
     * @param numMatchingItems The number of matching items the user has for this recipe.
     * @return A RecipeLoadDTO object.
     */
    public static RecipeLoadDTO toRecipeLoadDTO(Recipe recipe, int numMatchingItems) {
        return RecipeLoadDTO
                .builder()
                .recipeId(recipe.getRecipeId())
                .recipeName(recipe.getRecipeName())
                .description(recipe.getDescription())
                .author(recipe.getAuthor())
                .difficulty(recipe.getDifficulty())
                .servingSize(recipe.getServingSize())
                .thumbnail(recipe.getThumbnailLink())
                .cookingTime(recipe.getCookTime())
                .numMatchingItems(numMatchingItems)
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

    /**
     * Maps a Recipe object to a RecipeSuggestionLoad object.
     * @param recipe The Recipe object to be mapped.
     * @param userId The ID of the user the recipe is suggested to.
     * @return A RecipeSuggestionLoad object.
     */
    public static RecipeSuggestionLoad toRecipeSuggestionLoadDTO(Recipe recipe, Long userId){
        return RecipeSuggestionLoad.builder()
                .recipeLoadDTO(toRecipeLoadDTO(recipe))
                .UserId(userId)
                .build();
    }

}
