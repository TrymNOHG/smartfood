package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Nested
    class Recipe_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                Recipe recipe = new Recipe();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                Recipe recipe = new Recipe(1L, 50, new ArrayList<>());
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                Recipe recipe = Recipe
                        .builder()
                        .recipeId(1L)
                        .cookTime(50)
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class Recipe_can_properly_get {
        Recipe getRecipe() {
            return new Recipe(1L, 50, new ArrayList<>());
        }

        @Test
        void id() {
            Recipe recipe = getRecipe();
            Long expectedId = 1L;
            Long actualId = recipe.getRecipeId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void cook_time() {
            Recipe recipe = getRecipe();
            double expectedCookTime = 50;

            double actualCookTime = recipe.getCookTime();

            assertEquals(expectedCookTime, actualCookTime);
        }

        @Test
        void recipe_items() {
            Recipe recipe = getRecipe();
            List<RecipeItems> expectedItems = new ArrayList<>();

            List<RecipeItems> actualItems = recipe.getItemsInRecipe();

            assertEquals(expectedItems, actualItems);
        }


    }

    @Nested
    class Recipe_can_properly_set {
        Recipe getRecipe() {
            return new Recipe(1L, 50, new ArrayList<>());
        }

        @Test
        void cook_time() {
            Recipe recipe = getRecipe();
            double expectedCookTime = 100;

            recipe.setCookTime(expectedCookTime);
            double actualCookTime = recipe.getCookTime();

            assertEquals(expectedCookTime, actualCookTime);
        }

    }

}