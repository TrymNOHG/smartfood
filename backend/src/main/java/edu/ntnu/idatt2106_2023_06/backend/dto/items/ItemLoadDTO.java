package edu.ntnu.idatt2106_2023_06.backend.dto.items;

import lombok.NonNull;

public record ItemLoadDTO(@NonNull Long itemId, @NonNull String name, String description,
                          @NonNull String store, double price,
                          String image, int quantity, Boolean suggestion) {
}
