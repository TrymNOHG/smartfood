package edu.ntnu.idatt2106_2023_06.backend.dto.stat;

import lombok.NonNull;

import java.util.Date;

public record StatDTO(@NonNull Long statId,
                      @NonNull Integer statValue,
                      @NonNull Date timestamp,
                      @NonNull Long fridgeId,
                      @NonNull Long statTypeId,
                      @NonNull Long userId,
                      @NonNull String itemName,
                      @NonNull String storeName) {
}
