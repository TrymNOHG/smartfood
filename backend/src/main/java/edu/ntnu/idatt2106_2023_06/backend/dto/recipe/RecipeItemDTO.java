package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record RecipeItemDTO(@NonNull Long itemId, @NonNull String name,
                            double price, double quantity,
                            String unitOfMeasurement) {
}
