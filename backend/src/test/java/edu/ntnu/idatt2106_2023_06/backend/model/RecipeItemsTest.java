package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.items.*;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeItemId;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipePart;
import edu.ntnu.idatt2106_2023_06.backend.utils.UnitType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

class RecipeItemsTest {

    @Nested
    class RecipeItems_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                RecipeItems recipeItem = new RecipeItems();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                RecipeItems recipeItem = new RecipeItems(null, new Item(), new RecipePart(), 1, "L");
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                RecipeItems recipeItems = RecipeItems
                        .builder()
                        .item(new Item())
                        .recipePart(new RecipePart())
                        .id(null)
                        .quantity(1)
                        .unitOfMeasurement("L")
                        .build();

            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class Null_columns_constructors {

        @Test
        void item_id_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                RecipeItems recipeItem = new RecipeItems(null, null, new RecipePart(), 1, "L");

            });
        }

        @Test
        void recipe_part_id_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                RecipeItems recipeItem = new RecipeItems(null, new Item(), null, 1, "L");

            });
        }

        @Test
        void unit_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                RecipeItems recipeItem = new RecipeItems(null, new Item(), new RecipePart(), 1, null);

            });
        }

    }

    @Nested
    class Null_variables{

        @Test
        void item_id_cannot_be_set_to_null(){
            RecipeItems recipeItem = new RecipeItems(null, new Item(), new RecipePart(), 1, "L");
            assertThrows(NullPointerException.class, () -> {
                recipeItem.setItem(null);
            });
        }

        @Test
        void recipe_part_id_cannot_be_set_to_null(){
            RecipeItems recipeItem = new RecipeItems(null, new Item(), new RecipePart(), 1, "L");
            assertThrows(NullPointerException.class, () -> {
                recipeItem.setRecipePart(null);
            });
        }

        @Test
        void unit_cannot_be_set_to_null(){
            RecipeItems recipeItem = new RecipeItems(null, new Item(), new RecipePart(), 1, "L");
            assertThrows(NullPointerException.class, () -> {
                recipeItem.setUnitOfMeasurement(null);
            });
        }
    }

    @Nested
    class Getters{

        @Test
        void item_getter_returns_correct_value(){
            RecipePart recipePart = new RecipePart(1L, new Recipe(), "Make dough",
                     new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                     "picture.png", "12345678", 100.0, "l", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            RecipeItems recipeItems = new RecipeItems(new RecipeItemId(recipePart.getRecipePartId(), item.getItemId()), item, recipePart, 1, "L");
            assertEquals(item, recipeItems.getItem());
        }

        @Test
        void recipe_getter_returns_correct_value(){
            RecipePart recipePart = new RecipePart(1L, new Recipe(), "Make dough",
                    new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    "picture.png", "12345678", 100.0, "l", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            RecipeItems recipeItems = new RecipeItems(new RecipeItemId(recipePart.getRecipePartId(), item.getItemId()), item, recipePart, 1, "L");
            assertEquals(recipePart, recipeItems.getRecipePart());
        }

        @Test
        void quantity_returns_correct_value(){
            RecipePart recipePart = new RecipePart(1L, new Recipe(), "Make dough",
                    new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    "picture.png", "12345678", 100.0, "l", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            RecipeItems recipeItems = new RecipeItems(new RecipeItemId(recipePart.getRecipePartId(), item.getItemId()), item, recipePart, 1, "L");
            assertEquals(1, recipeItems.getQuantity());
        }

        @Test
        void unit_getter_returns_correct_value(){
            RecipePart recipePart = new RecipePart(1L, new Recipe(), "Make dough",
                    new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    "picture.png", "12345678", 100.0, "l", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            RecipeItems recipeItems = new RecipeItems(new RecipeItemId(recipePart.getRecipePartId(), item.getItemId()), item, recipePart, 1, "L");
            assertEquals("L", recipeItems.getUnitOfMeasurement());
        }
    }


}