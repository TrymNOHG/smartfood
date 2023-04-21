package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 * This exception is thrown when an item is not found in the database. It extends the NotFoundException class.
 *
 *  @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public class ItemNotFoundException extends NotFoundException {

    /**
     * This method constructs a new ItemNotFoundException with the given itemName.
     *
     * @param itemName The name of the item that was not found, given as a String.
     */
    public ItemNotFoundException(String itemName) {
        super("Item", itemName);
    }

    /**
     * This method constructs a new ItemNotFoundException with the given itemId.
     *
     * @param itemId The id of the item that was not found, given as a Long Object.
     */
    public ItemNotFoundException(Long itemId) {
        super("Item", itemId);
    }

}
