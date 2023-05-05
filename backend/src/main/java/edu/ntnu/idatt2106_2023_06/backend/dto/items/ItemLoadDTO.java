package edu.ntnu.idatt2106_2023_06.backend.dto.items;

import lombok.NonNull;

/**
 * This record represents a DTO for loading an item.
 * @param itemId        The ID of the item.
 * @param name          The name of the product.
 * @param description   The description of the product.
 * @param store         The name of the store the item comes from.
 * @param price         The price of the item.
 * @param image         The link to the image of the item.
 * @param quantity      The amount present of the item.
 * @param suggestion    Whether the item is a suggestion or not.
 */
public record ItemLoadDTO(@NonNull Long itemId, @NonNull String name, String description,
                          @NonNull String store, double price,
                          String image, int quantity, Boolean suggestion) {
}
