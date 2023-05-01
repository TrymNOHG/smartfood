package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeAllergenId;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeItemId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RecipeAllergenIdTest {

    @Nested
    class Constructor_test{

        @Test
        void empty_constructor_does_not_throw_errors(){
            assertDoesNotThrow(() -> {
                new RecipeAllergenId();
            });
        }

        @Test
        void constructor_does_not_throw_errors(){
            assertDoesNotThrow(() -> {
                new RecipeAllergenId(1L, 1L);
            });
        }
    }

}
