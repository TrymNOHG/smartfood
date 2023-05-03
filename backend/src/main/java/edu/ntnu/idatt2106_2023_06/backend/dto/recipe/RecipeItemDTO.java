package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class RecipeItemDTO {
            @NonNull private Long itemId;
            @NonNull private String name;
            private double price, quantity;
            private String unitOfMeasurement;
            private boolean hasItem;
}
