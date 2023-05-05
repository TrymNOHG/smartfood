package edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list;

import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * This record represents a dto for updating a shopping list item.
 * @param itemId        The ID of the item to update.
 * @param fridgeId      The ID of the fridge the shopping list belongs to.
 * @param quantity      The new amount.
 * @param suggestion    Whether the update is a suggestion or not.
 */
public record ShoppingItemUpdateDTO(@NonNull Long itemId, @NonNull Long fridgeId,
                                    @Nullable Integer quantity, @Nullable Boolean suggestion) {
}
