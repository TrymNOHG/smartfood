package edu.ntnu.idatt2106_2023_06.backend.mapper.recipe;

import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.ItemRecipeScore;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.ItemRecipeScoreId;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;

/**

 Mapper for {@link ItemRecipeScore} entity.
 */
public class ItemRecipeScoreMapper {

    /**
     * Maps an {@link Item}, a {@link Recipe}, and a {@code score} to an {@link ItemRecipeScore} object.
     *
     * @param item the {@link Item} to be mapped
     * @param recipe the {@link Recipe} to be mapped
     * @param score the score to be mapped
     * @return an {@link ItemRecipeScore} object
     */
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
