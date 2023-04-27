package edu.ntnu.idatt2106_2023_06.backend.dto.items;

import lombok.Builder;
import lombok.NonNull;

/**
 * A DTO representing an item.
 * This method contains information such as name, description, store, price, purchase date, expiration date, image,
 * and quantity.
 *
 * @param name              Name of item, given as a String
 * @param description       Description, given as a String
 * @param store             Name of store, given as a String
 * @param price             Price of item, given as a double
 * @param image             Link of image, given as a String
 * @param quantity          Quantity of items, given as int
 *
 * @author Trym Hamer Gudvangen
 */
@Builder
public record ItemDTO(@NonNull String name, String description,
                      @NonNull String store, double price,
                      String image, int quantity, Boolean suggestion) {
}
