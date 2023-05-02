package edu.ntnu.idatt2106_2023_06.backend.mapper.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.ItemRecipeScore;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.ItemRecipeScoreId;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;

public class ItemRecipeScoreMapper {

    public static ItemRecipeScore toItemRecipeScore(Item item, Recipe recipe, double score) {
        return ItemRecipeScore
                .builder()
                .itemRecipeScoreId(new ItemRecipeScoreId(item.getItemId(), recipe.getRecipeId()))
                .item(item)
                .recipe(recipe)
                .score(score)
                .build();
    }

}
