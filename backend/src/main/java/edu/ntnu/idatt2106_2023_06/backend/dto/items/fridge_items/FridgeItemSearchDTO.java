package edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items;

import lombok.NonNull;

public record FridgeItemSearchDTO(@NonNull Long fridgeId,
                                  @NonNull String productName,
                                  @NonNull String sortField,
                                  @NonNull String sortOrder,
                                  @NonNull Integer page,
                                  @NonNull Integer pageSize) {
}