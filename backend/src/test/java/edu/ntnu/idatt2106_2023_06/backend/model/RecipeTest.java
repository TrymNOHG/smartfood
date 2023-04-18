package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Nested
    class Recipe_can_properly_get {
        Recipe getRecipe() {
            return new Recipe(1L, 50);
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

    }

    @Nested
    class Recipe_can_properly_set {
        Recipe getRecipe() {
            return new Recipe(1L, 50);
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