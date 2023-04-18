package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecipeItemsTest {

    @Nested
    class Null_columns_constructors {

        @Test
        void item_id_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                RecipeItems recipeItem = new RecipeItems(null, null, new Recipe());
            });
        }

        @Test
        void recipe_id_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                RecipeItems recipeItem = new RecipeItems(null, new Item(), null);
            });
        }

    }

    @Nested
    class Null_variables{

        @Test
        void item_id_cannot_be_set_to_null(){
            RecipeItems recipeItem = new RecipeItems(null, new Item(), new Recipe());
            assertThrows(NullPointerException.class, () -> {
                recipeItem.setItem(null);
            });
        }

        @Test
        void recipe_id_cannot_be_set_to_null(){
            RecipeItems recipeItem = new RecipeItems(null, new Item(), new Recipe());
            assertThrows(NullPointerException.class, () -> {
                recipeItem.setRecipe(null);
            });
        }
    }

    @Nested
    class Getters{

        @Test
        void item_getter_returns_correct_value(){
            Recipe recipe = new Recipe(1L, 50);
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", new FoodCategory(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date());
            RecipeItems recipeItems = new RecipeItems(new RecipeItemId(recipe.getRecipeId(), item.getItemId()), item, recipe);
            assertEquals(item, recipeItems.getItem());
        }

        @Test
        void recipe_getter_returns_correct_value(){
            Recipe recipe = new Recipe(1L, 50);
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", new FoodCategory(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date());
            RecipeItems recipeItems = new RecipeItems(new RecipeItemId(recipe.getRecipeId(), item.getItemId()), item, recipe);
            assertEquals(recipe, recipeItems.getRecipe());
        }
    }


}