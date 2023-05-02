package edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public record RecipeShoppingDTO(@NonNull Long fridgeId, @NonNull List<Long> itemIds) {
}
