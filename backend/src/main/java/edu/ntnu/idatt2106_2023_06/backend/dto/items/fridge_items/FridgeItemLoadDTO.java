package edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items;

import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder
public record FridgeItemLoadDTO(@NonNull Long itemId, @NonNull String name,
                                String description, @NonNull String store,
                                double price, String image, int quantity,
                                @NonNull LocalDateTime purchaseDate, @NonNull LocalDateTime expirationDate,
                                String ean) {

}
