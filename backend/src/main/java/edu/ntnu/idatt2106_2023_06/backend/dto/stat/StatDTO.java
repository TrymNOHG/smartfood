package edu.ntnu.idatt2106_2023_06.backend.dto.stat;

import lombok.NonNull;

import java.util.Date;

/**
 * This record represents a general statistics DTO.
 */
public record StatDTO(@NonNull Long statId,
                      @NonNull Double statValue,
                      @NonNull Date timestamp,
                      @NonNull Long fridgeId,
                      @NonNull Long statTypeId,
                      @NonNull Long userId,
                      @NonNull String itemName,
                      @NonNull String storeName) {
}
