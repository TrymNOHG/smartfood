package edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items;

import lombok.NonNull;

/**
 *  Data Transfer Object for searching Fridge items.
 *  This record contains information about the fridgeId, productName to search for,
 *  the field and order to sort the search results, the page and pageSize of the search results.
 */
public record FridgeItemSearchDTO(@NonNull Long fridgeId,
                                  @NonNull String productName,
                                  @NonNull String sortField,
                                  @NonNull String sortOrder,
                                  @NonNull Integer page,
                                  @NonNull Integer pageSize) {
}