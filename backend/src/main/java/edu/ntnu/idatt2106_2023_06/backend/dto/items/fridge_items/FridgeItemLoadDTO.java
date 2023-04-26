package edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items;

import lombok.NonNull;

import java.util.Date;

public record FridgeItemLoadDTO(@NonNull Long itemId, @NonNull String name,
                                String description, @NonNull String store,
                                double price, String image, int quantity,
                                @NonNull Date purchaseDate, @NonNull Date expirationDate) {

}
