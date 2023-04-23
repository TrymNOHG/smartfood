package edu.ntnu.idatt2106_2023_06.backend.dto.stat;

import lombok.NonNull;

public record StatAddDTO(@NonNull Long fridgeId, @NonNull Long statTypeId,
                         @NonNull Long userId, @NonNull Integer statValue) {
}
