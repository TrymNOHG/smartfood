package edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list;

import lombok.Builder;
import lombok.NonNull;


/**
 *  A data transfer object representing a shopping list item to be loaded.
 */
@Builder
public record ShoppingListLoadDTO(@NonNull Long itemId, @NonNull String name,
                                  String description, @NonNull String store,
                                  double price, String image, int quantity, Boolean suggestion,
                                  String ean) {
}

