package edu.ntnu.idatt2106_2023_06.backend.dto.items;

import lombok.NonNull;

public record ItemMoveDTO(@NonNull Long itemId, @NonNull Long fridgeId) {
}
