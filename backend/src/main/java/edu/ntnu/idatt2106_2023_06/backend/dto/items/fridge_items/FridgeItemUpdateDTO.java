package edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;


/**
 * This record represents a DTO for updating a fridge item.
 */
@Builder
public record FridgeItemUpdateDTO(@NonNull Long itemId, @NonNull Long fridgeId,
                                  @Nullable Double amount, @Nullable LocalDateTime purchaseDate,
                                  @Nullable LocalDateTime expirationDate) {
}
