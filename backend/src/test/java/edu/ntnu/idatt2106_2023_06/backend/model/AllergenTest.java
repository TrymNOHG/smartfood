package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Allergen;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeAllergen;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AllergenTest {


    @Nested
    class Allergen_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                Allergen allergen = new Allergen();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                Allergen allergen = new Allergen(1L, "Dairy", new HashSet<>());
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                Allergen allergen = Allergen
                        .builder()
                        .allergenId(1L)
                        .allergenName("Dairy")
                        .recipeAllergenSet(new HashSet<>())
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class Allergen_can_properly_get {
        Allergen getAllergen() {
            return new Allergen(1L, "Dairy", new HashSet<>());
        }

        @Test
        void id() {
            Allergen allergen = getAllergen();
            Long expectedId = 1L;
            Long actualId = allergen.getAllergenId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void allergen_name() {
            Allergen allergen = getAllergen();
            String expectedAllergenName = allergen.getAllergenName();

            String actualAllergenName = allergen.getAllergenName();

            assertEquals(expectedAllergenName, actualAllergenName);
        }

        @Test
        void recipe_allergens() {
            Allergen allergen = getAllergen();
            Set<RecipeAllergen> recipeAllergenSet = new HashSet<>();

            Set<RecipeAllergen> actualRecipeAllergenSet = allergen.getRecipeAllergenSet();
            assertEquals(recipeAllergenSet, actualRecipeAllergenSet);
        }

    }

    @Nested
    class Allergen_can_properly_set {
        Allergen getAllergen() {
            return new Allergen(1L, "Dairy", new HashSet<>());
        }
        @Test
        void id() {
            Allergen allergen = getAllergen();
            Long expectedId = 2L;

            allergen.setAllergenId(expectedId);
            Long actualId = allergen.getAllergenId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void allergen_name() {
            Allergen allergen = getAllergen();
            String expectedAllergenName = allergen.getAllergenName();

            allergen.setAllergenName(expectedAllergenName);
            String actualAllergenName = allergen.getAllergenName();

            assertEquals(expectedAllergenName, actualAllergenName);
        }

        @Test
        void recipe_allergens() {
            Allergen allergen = getAllergen();
            Set<RecipeAllergen> recipeAllergenSet = new HashSet<>();

            allergen.setRecipeAllergenSet(recipeAllergenSet);
            Set<RecipeAllergen> actualRecipeAllergenSet = allergen.getRecipeAllergenSet();
            assertEquals(recipeAllergenSet, actualRecipeAllergenSet);
        }

    }


}
