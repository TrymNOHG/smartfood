package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeItemId;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipePart;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RecipePartTest {

    @Nested
    class Recipe_Part_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                RecipePart recipePart = new RecipePart();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                RecipePart recipePart = new RecipePart(1L, new Recipe(), "Make dough",
                        "Step 1. Knead dough", new ArrayList<>());
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                RecipePart recipePart = RecipePart
                        .builder()
                        .recipePartId(1L)
                        .recipe(new Recipe())
                        .partName("Make dough")
                        .instructions("Step 1. ...")
                        .itemsInRecipe(new ArrayList<>())
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class RecipePart_can_properly_get {
        RecipePart getRecipePart() {
            return new RecipePart(1L, new Recipe(), "Make dough",
                    "Step 1. Knead dough", new ArrayList<>());
        }

        @Test
        void id() {
            RecipePart recipePart = getRecipePart();
            Long expectedId = 1L;
            Long actualId = recipePart.getRecipePartId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void recipe() {
            RecipePart recipePart = getRecipePart();
            Recipe expectedRecipe = new Recipe();

            Recipe actualRecipe = recipePart.getRecipe();

            assertEquals(expectedRecipe, actualRecipe);
        }

        @Test
        void recipePartName() {
            RecipePart recipePart = getRecipePart();
            String expectedName = "Make dough";
            String actualName = recipePart.getPartName();

            assertEquals(expectedName, actualName);
        }

        @Test
        void instructions() {
            RecipePart recipePart = getRecipePart();
            String expectedInstructions = "Step 1. Knead dough";
            String actualInstructions = recipePart.getInstructions();

            assertEquals(expectedInstructions, actualInstructions);
        }


        @Test
        void recipe_part_items() {
            RecipePart recipePart = getRecipePart();
            List<RecipeItems> expectedItems = new ArrayList<>();

            List<RecipeItems> actualItems = recipePart.getItemsInRecipe();

            assertEquals(expectedItems, actualItems);
        }


    }

    @Nested
    class RecipePart_can_properly_set {
        RecipePart getRecipePart() {
            return new RecipePart(1L, new Recipe(), "Make dough",
                    "Step 1. Knead dough", new ArrayList<>());
        }

        @Test
        void id() {
            RecipePart recipePart = getRecipePart();
            Long expectedId = 2L;

            recipePart.setRecipePartId(expectedId);
            Long actualId = recipePart.getRecipePartId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void recipe() {
            RecipePart recipePart = getRecipePart();
            Recipe expectedRecipe = new Recipe();

            recipePart.setRecipe(expectedRecipe);
            Recipe actualRecipe = recipePart.getRecipe();

            assertEquals(expectedRecipe, actualRecipe);
        }

        @Test
        void recipePartName() {
            RecipePart recipePart = getRecipePart();
            String expectedName = "Make sauce";

            recipePart.setPartName(expectedName);
            String actualName = recipePart.getPartName();

            assertEquals(expectedName, actualName);
        }

        @Test
        void instructions() {
            RecipePart recipePart = getRecipePart();
            String expectedInstructions = "Step 2. Mix spices";

            recipePart.setInstructions(expectedInstructions);
            String actualInstructions = recipePart.getInstructions();

            assertEquals(expectedInstructions, actualInstructions);
        }


        @Test
        void recipe_part_items() {
            RecipePart recipePart = getRecipePart();
            List<RecipeItems> expectedItems = new ArrayList<>();

            recipePart.setItemsInRecipe(expectedItems);
            List<RecipeItems> actualItems = recipePart.getItemsInRecipe();

            assertEquals(expectedItems, actualItems);
        }


    }

}
