package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Instructions;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipePart;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeSuggestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
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
                Recipe recipe = new Recipe(1L, "Grønnsakslasagne med søtpotet, aubergine og grønnkål",
                         "En vegetarisk oppskrift på grønnsakslasagne full av smak. Her er kjøttdeigen " +
                                 "byttet ut med søtpotet og aubergine, ostesausen med en blomkålpuré og lasagneplatene " +
                                 "med ulike grønnsaker i tynne skiver. Perfekt vegetarlasagne hvor du kan bruke en " +
                                 "rekke grønnsaksrester.\n", "Meny", 5, 1, "image.png",
                        50, new ArrayList<>(),  new ArrayList<>(),new HashSet<>(), new ArrayList<>());
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
                        .recipeName("Lasagna")
                        .description("Delicious lasagna")
                        .recipeParts(new ArrayList<>())
                        .author("Meny")
                        .thumbnailLink("image.png")
                        .servingSize(5)
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
            return new Recipe(1L, "Grønnsakslasagne med søtpotet, aubergine og grønnkål",
                    "En vegetarisk oppskrift på grønnsakslasagne full av smak. Her er kjøttdeigen " +
                            "byttet ut med søtpotet og aubergine, ostesausen med en blomkålpuré og lasagneplatene " +
                            "med ulike grønnsaker i tynne skiver. Perfekt vegetarlasagne hvor du kan bruke en " +
                            "rekke grønnsaksrester.\n", "Meny", 5, 1, "image.png",
                    50, new ArrayList<>(),  new ArrayList<>(), new HashSet<>(), new ArrayList<>());
        }

        @Test
        void id() {
            Recipe recipe = getRecipe();
            Long expectedId = 1L;
            Long actualId = recipe.getRecipeId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void recipeName() {
            Recipe recipe = getRecipe();
            String expectedName = "Grønnsakslasagne med søtpotet, aubergine og grønnkål";
            String actualName = recipe.getRecipeName();

            assertEquals(expectedName, actualName);
        }

        @Test
        void description() {
            Recipe recipe = getRecipe();
            String expectedDescription = "En vegetarisk oppskrift på grønnsakslasagne full av smak. Her er kjøttdeigen " +
                    "byttet ut med søtpotet og aubergine, ostesausen med en blomkålpuré og lasagneplatene med ulike " +
                    "grønnsaker i tynne skiver. Perfekt vegetarlasagne hvor du kan bruke en rekke grønnsaksrester.\n";
            String actualDescription = recipe.getDescription();

            assertEquals(expectedDescription, actualDescription);
        }

        @Test
        void author() {
            Recipe recipe = getRecipe();
            String expectedAuthor = "Meny";
            String actualAuthor = recipe.getAuthor();

            assertEquals(expectedAuthor, actualAuthor);
        }

        @Test
        void servingSize() {
            Recipe recipe = getRecipe();
            int expectedServingSize = 5;
            int actualServingSize = recipe.getServingSize();

            assertEquals(expectedServingSize, actualServingSize);
        }

        @Test
        void thumbnail_link() {
            Recipe recipe = getRecipe();
            String expectedLink = "image.png";
            String actualLink = recipe.getThumbnailLink();

            assertEquals(expectedLink, actualLink);
        }


        @Test
        void cook_time() {
            Recipe recipe = getRecipe();
            double expectedCookTime = 50;

            double actualCookTime = recipe.getCookTime();

            assertEquals(expectedCookTime, actualCookTime);
        }

        @Test
        void recipe_parts() {
            Recipe recipe = getRecipe();
            List<RecipePart> expectedParts= new ArrayList<>();

            List<RecipePart> actualPars = recipe.getRecipeParts();

            assertEquals(expectedParts, actualPars);
        }

        @Test
        void instructions() {
            Recipe recipe = getRecipe();
            List<Instructions> expectedInstructions = new ArrayList<>();

            List<Instructions> actualInstructions = recipe.getInstructions();

            assertEquals(expectedInstructions, actualInstructions);
        }

        @Test
        void Suggestions() {
            Recipe recipe = getRecipe();
            List<RecipeSuggestion> expectedSug = new ArrayList<>();

            List<RecipeSuggestion> actualSug = recipe.getRecipeSuggestion();

            assertEquals(expectedSug, actualSug);
        }


    }

    @Nested
    class Recipe_can_properly_set {
        Recipe getRecipe() {
            return new Recipe(1L, "Grønnsakslasagne med søtpotet, aubergine og grønnkål",
                    "En vegetarisk oppskrift på grønnsakslasagne full av smak. Her er kjøttdeigen " +
                            "byttet ut med søtpotet og aubergine, ostesausen med en blomkålpuré og lasagneplatene " +
                            "med ulike grønnsaker i tynne skiver. Perfekt vegetarlasagne hvor du kan bruke en " +
                            "rekke grønnsaksrester.\n", "Meny", 5, 1, "image.png",
                    50, new ArrayList<>(),  new ArrayList<>(), new HashSet<>(), new ArrayList<>());
        }

        @Test
        void recipeName() {
            Recipe recipe = getRecipe();
            String expectedName = "Lasagne";

            recipe.setRecipeName(expectedName);
            String actualName = recipe.getRecipeName();

            assertEquals(expectedName, actualName);
        }

        @Test
        void description() {
            Recipe recipe = getRecipe();
            String expectedDescription = "Pasta-like dish";

            recipe.setDescription(expectedDescription);
            String actualDescription = recipe.getDescription();

            assertEquals(expectedDescription, actualDescription);
        }

        @Test
        void author() {
            Recipe recipe = getRecipe();
            String expectedAuthor = "Coop";

            recipe.setAuthor(expectedAuthor);
            String actualAuthor = recipe.getAuthor();

            assertEquals(expectedAuthor, actualAuthor);
        }

        @Test
        void servingSize() {
            Recipe recipe = getRecipe();
            int expectedServingSize = 4;

            recipe.setServingSize(expectedServingSize);
            int actualServingSize = recipe.getServingSize();

            assertEquals(expectedServingSize, actualServingSize);
        }

        @Test
        void thumbnail_link() {
            Recipe recipe = getRecipe();
            String expectedLink = "image.jpeg";

            recipe.setThumbnailLink(expectedLink);
            String actualLink = recipe.getThumbnailLink();

            assertEquals(expectedLink, actualLink);
        }

        @Test
        void cook_time() {
            Recipe recipe = getRecipe();
            double expectedCookTime = 100;

            recipe.setCookTime(expectedCookTime);
            double actualCookTime = recipe.getCookTime();

            assertEquals(expectedCookTime, actualCookTime);
        }

        @Test
        void instructions() {
            Recipe recipe = getRecipe();
            List<Instructions> expectedInstructions = new ArrayList<>();

            recipe.setInstructions(expectedInstructions);
            List<Instructions> actualInstructions = recipe.getInstructions();

            assertEquals(expectedInstructions, actualInstructions);
        }

    }

    @Test
    public void testHashCode() {
        Recipe recipe1 = Recipe.builder()
                .recipeName("Spaghetti Bolognese")
                .description("A classic Italian dish")
                .author("Jamie Oliver")
                .servingSize(4)
                .difficulty(2)
                .thumbnailLink("https://www.example.com/spaghetti-bolognese.jpg")
                .cookTime(30.0)
                .build();

        Recipe recipe2 = Recipe.builder()
                .recipeName("Spaghetti Bolognese")
                .description("A classic Italian dish")
                .author("Jamie Oliver")
                .servingSize(4)
                .difficulty(2)
                .thumbnailLink("https://www.example.com/spaghetti-bolognese.jpg")
                .cookTime(30.0)
                .build();

        Assertions.assertEquals(recipe1.hashCode(), recipe2.hashCode());
    }

}