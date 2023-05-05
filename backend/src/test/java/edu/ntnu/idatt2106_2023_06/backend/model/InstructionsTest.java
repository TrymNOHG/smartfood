package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Instructions;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InstructionsTest {

    @Test
    void instructionsConstructorTest() {
        // Given
        Long recipeInstructionId = 1L;
        Recipe recipe = new Recipe();
        String instruction = "Add 1 cup of sugar";
        String imageLink = "http://example.com/image.jpg";

        // When
        Instructions instructions = new Instructions(recipeInstructionId, recipe, instruction, imageLink);

        // Then
        assertEquals(recipeInstructionId, instructions.getRecipeInstructionId());
        assertEquals(recipe, instructions.getRecipe());
        assertEquals(instruction, instructions.getInstruction());
        assertEquals(imageLink, instructions.getImageLink());
    }

    @Test
    void instructionsToStringTest() {
        // Given
        Long recipeInstructionId = 1L;
        Recipe recipe = Recipe
                .builder()
                .recipeId(1L)
                .recipeName("Lasagna")
                .description("Delicious lasagna")
                .recipeParts(new ArrayList<>())
                .author("Meny")
                .thumbnailLink("image.png")
                .servingSize(5)
                .cookTime(50)
                .build();
        String instruction = "Add 1 cup of sugar";
        String imageLink = "http://example.com/image.jpg";
        Instructions instructions = Instructions.builder().recipeInstructionId(recipeInstructionId)
                .recipe(recipe)
                .instruction(instruction)
                .imageLink(imageLink)
                .build();

        // When
        String actual = instructions.toString();

        // Then
        String expected = "Instructions(recipeInstructionId=1, instruction=Add 1 cup of sugar, imageLink=http://example.com/image.jpg)";
        assertEquals(expected, actual);
    }

    @Test
    void testSetRecipeInstructionId() {
        Long expectedId = 123L;
        Instructions instructions = new Instructions();
        instructions.setRecipeInstructionId(expectedId);
        Long actualId = instructions.getRecipeInstructionId();
        assertEquals(expectedId, actualId);
    }
}
