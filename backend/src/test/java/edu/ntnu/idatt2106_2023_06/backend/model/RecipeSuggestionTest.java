package edu.ntnu.idatt2106_2023_06.backend.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeSuggestion;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeSuggestionId;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;

public class RecipeSuggestionTest {

    private RecipeSuggestion recipeSuggestion;
    private RecipeSuggestionId recipeSuggestionId;
    private Recipe recipe;
    private Fridge fridge;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        recipe = new Recipe();
        fridge = new Fridge();
        user = new User();
        recipeSuggestionId = new RecipeSuggestionId(1L, 1L, 1L);
        recipeSuggestion = RecipeSuggestion.builder()
                .id(recipeSuggestionId)
                .recipe(recipe)
                .fridge(fridge)
                .user(user)
                .build();
    }

    @Test
    void testGetRecipe() {
        assertEquals(recipe, recipeSuggestion.getRecipe());
    }

    @Test
    void testGetFridge() {
        assertEquals(fridge, recipeSuggestion.getFridge());
    }

    @Test
    void testGetUser() {
        assertEquals(user, recipeSuggestion.getUser());
    }


    @Test
    void testAllArgsConstructor() {
        RecipeSuggestionId id = new RecipeSuggestionId(1L, 2L, 3L);
        Recipe r = new Recipe();
        Fridge f = new Fridge();
        User u = new User();
        RecipeSuggestion suggestion = new RecipeSuggestion(id, r, f, u);
        assertEquals(id, suggestion.getId());
        assertEquals(r, suggestion.getRecipe());
        assertEquals(f, suggestion.getFridge());
        assertEquals(u, suggestion.getUser());
    }

    // Test getters
    @Test
    void testGetId() {
        assertEquals(recipeSuggestionId, recipeSuggestion.getId());
    }


    // Test setters
    @Test
    void testSetId() {
        RecipeSuggestionId newId = new RecipeSuggestionId(10L, 20L, 30L);
        recipeSuggestion.setId(newId);
        assertEquals(newId, recipeSuggestion.getId());
    }

    @Test
    void testSetRecipe() {
        Recipe newRecipe = new Recipe();
        recipeSuggestion.setRecipe(newRecipe);
        assertEquals(newRecipe, recipeSuggestion.getRecipe());
    }

    @Test
    void testSetFridge() {
        Fridge newFridge = new Fridge();
        recipeSuggestion.setFridge(newFridge);
        assertEquals(newFridge, recipeSuggestion.getFridge());
    }

    @Test
    void testSetUser() {
        User newUser = new User();
        recipeSuggestion.setUser(newUser);
        assertEquals(newUser, recipeSuggestion.getUser());
    }
}

