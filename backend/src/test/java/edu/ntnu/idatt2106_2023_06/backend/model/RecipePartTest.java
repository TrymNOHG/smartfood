package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipePart;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
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
                RecipePart recipePart = new RecipePart(1L, new Recipe(), "Make dough", new ArrayList<>());
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
            return new RecipePart(1L, new Recipe(1L, "Grønnsakslasagne med søtpotet, aubergine og grønnkål",
                    "En vegetarisk oppskrift på grønnsakslasagne full av smak. Her er kjøttdeigen " +
                            "byttet ut med søtpotet og aubergine, ostesausen med en blomkålpuré og lasagneplatene " +
                            "med ulike grønnsaker i tynne skiver. Perfekt vegetarlasagne hvor du kan bruke en " +
                            "rekke grønnsaksrester.\n", "Meny", 5, 1, "image.png",
                    50, new ArrayList<>(),  new ArrayList<>(), new HashSet<>()), "Make dough", new ArrayList<>());
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
            Recipe expectedRecipe = new Recipe(1L, "Grønnsakslasagne med søtpotet, aubergine og grønnkål",
                    "En vegetarisk oppskrift på grønnsakslasagne full av smak. Her er kjøttdeigen " +
                            "byttet ut med søtpotet og aubergine, ostesausen med en blomkålpuré og lasagneplatene " +
                            "med ulike grønnsaker i tynne skiver. Perfekt vegetarlasagne hvor du kan bruke en " +
                            "rekke grønnsaksrester.\n", "Meny", 5, 1, "image.png",
                    50, new ArrayList<>(),  new ArrayList<>(), new HashSet<>());

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
                     new ArrayList<>());
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
        void recipe_part_items() {
            RecipePart recipePart = getRecipePart();
            List<RecipeItems> expectedItems = new ArrayList<>();

            recipePart.setItemsInRecipe(expectedItems);
            List<RecipeItems> actualItems = recipePart.getItemsInRecipe();

            assertEquals(expectedItems, actualItems);
        }


    }

}
