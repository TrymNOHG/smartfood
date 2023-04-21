package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 *  An exception to be thrown when a store is not found.
 *
 * @author Leon Egeberg Hesthaug, Trym Hamer Gudvangen
 */
public class StoreNotFoundException extends NotFoundException {

    /**
     * This method constructs a new StoreNotFoundException with the specified store name.
     * @param storeName The name of the store that was not found, given as a String
     */
    public StoreNotFoundException(String storeName) {
        super("Store", storeName);
    }

    /**
     * This method constructs a new StoreNotFoundException with the specified store id.
     * @param storeId The id of the store that was not found, given as a Long object.
     */
    public StoreNotFoundException(Long storeId) {
        super("Store", storeId);
    }
}
