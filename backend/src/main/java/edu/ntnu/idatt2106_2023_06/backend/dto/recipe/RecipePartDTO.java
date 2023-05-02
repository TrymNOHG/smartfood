package edu.ntnu.idatt2106_2023_06.backend.dto.recipe;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public record RecipePartDTO(String partName, @NonNull List<RecipeItemDTO> ingredients) {
}
