package edu.ntnu.idatt2106_2023_06.backend.dto.items;

import lombok.NonNull;

import java.util.Date;

public record ItemDTO(@NonNull String name, @NonNull String description,
                      @NonNull String store, double price, Date purchaseDate,
                      Date expirationDate, String image) {
}
