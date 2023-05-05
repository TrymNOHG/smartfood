package edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

/**
 * This record represents a DTO for the items to add to a shopping list from a recipe.
 * @param fridgeId  The fridge id connected to the shopping list, given as a Long object.
 * @param itemIds   The list of item ids to add to the shopping list.
 */
@Builder
public record RecipeShoppingDTO(@NonNull Long fridgeId, @NonNull List<Long> itemIds) {
}
