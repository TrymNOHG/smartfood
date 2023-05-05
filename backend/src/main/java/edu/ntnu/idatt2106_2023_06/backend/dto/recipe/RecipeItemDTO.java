package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.*;

/**
 * This record represents a dto for an ingredient in a recipe.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class RecipeItemDTO {
            @NonNull private Long itemId;
            @NonNull private String name;
            private double price, quantity;
            private String unitOfMeasurement, itemOriginalUnit;
            private Double fridgeAmount, itemOriginalAmount;
}
