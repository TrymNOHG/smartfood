package edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ShoppingListLoadDTO(@NonNull Long itemId, @NonNull String name,
                                  String description, @NonNull String store,
                                  double price, String image, int quantity, Boolean suggestion,
                                  String ean) {
}

