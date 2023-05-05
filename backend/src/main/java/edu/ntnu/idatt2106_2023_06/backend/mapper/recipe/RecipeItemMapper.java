package edu.ntnu.idatt2106_2023_06.backend.mapper.recipe;

import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeItems;

/**
 This class maps RecipeItems to RecipeItemDTO.
 */
public class RecipeItemMapper {

    /**
     * Maps RecipeItems to RecipeItemDTO.
     *
     * @param recipeItem the RecipeItems to map
     * @return the RecipeItemDTO that was mapped from the RecipeItems
     */
    public static RecipeItemDTO toRecipeItemDTO(RecipeItems recipeItem) {
        Item item = recipeItem.getItem();
        return RecipeItemDTO
                .builder()
                .itemId(item.getItemId())
                .name(item.getProductName())
                .quantity(recipeItem.getQuantity())
                .unitOfMeasurement(recipeItem.getUnitOfMeasurement())
                .price(item.getPrice())
                .itemOriginalAmount(item.getAmount())
                .itemOriginalUnit(item.getUnit())
                .fridgeAmount(0.0)
                .build();
    }

}
