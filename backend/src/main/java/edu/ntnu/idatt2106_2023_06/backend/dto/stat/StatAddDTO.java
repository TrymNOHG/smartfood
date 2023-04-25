package edu.ntnu.idatt2106_2023_06.backend.dto.stat;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record StatAddDTO(@NonNull Long fridgeId,
                         @NonNull Long statTypeId,
                         @NonNull Long userId,
                         @NonNull Double statValue,
                         @NonNull Integer quantity,
                         @NonNull String itemName,
                         @NonNull String storeName) {
}
