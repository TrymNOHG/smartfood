package edu.ntnu.idatt2106_2023_06.backend.model;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.ItemRecipeScore;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.ItemRecipeScoreId;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemRecipeScoreTest {

    private ItemRecipeScore itemRecipeScore;
    private ItemRecipeScoreId itemRecipeScoreId;
    private Item item;
    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        item = new Item();
        item.setItemId(1L);

        recipe = new Recipe();
        recipe.setRecipeId(2L);


        itemRecipeScoreId = new ItemRecipeScoreId(item.getItemId(), recipe.getRecipeId());
        itemRecipeScore = ItemRecipeScore.builder()
                .itemRecipeScoreId(itemRecipeScoreId)
                .item(item)
                .recipe(recipe)
                .score(0.8)
                .build();
    }

    @Test
    public void testSetAndGetItemRecipeScoreId() {
        ItemRecipeScoreId newItemRecipeScoreId = new ItemRecipeScoreId(3L, 4L);
        itemRecipeScore.setItemRecipeScoreId(newItemRecipeScoreId);

        assertEquals(newItemRecipeScoreId, itemRecipeScore.getItemRecipeScoreId());
    }

    @Test
    public void testSetAndGetItem() {
        Item newItem = new Item();
        newItem.setItemId(5L);
        itemRecipeScore.setItem(newItem);

        assertEquals(newItem, itemRecipeScore.getItem());
    }

    @Test
    public void testSetAndGetRecipe() {
        Recipe newRecipe = new Recipe();
        newRecipe.setRecipeId(6L);
        itemRecipeScore.setRecipe(newRecipe);

        assertEquals(newRecipe, itemRecipeScore.getRecipe());
    }

    @Test
    public void testSetAndGetScore() {
        itemRecipeScore.setScore(0.6);

        assertEquals(0.6, itemRecipeScore.getScore());
    }
}
