package edu.ntnu.idatt2106_2023_06.backend.dto.items;

/**
 *
 * @param itemName  Name of the item to remove, given as a String
 * @param store     Name of the store, given as a String
 * @param fridgeId  ID of the fridge, given as a Long object
 * @param quantity  Amount to remove, given as an int
 */
public record ItemRemoveDTO(
        String itemName,
        String store,
        Long fridgeId,
        int quantity) {

}
