package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class StoreNotFoundException extends NotFoundException {

    public StoreNotFoundException(String itemName) {
        super("Store", itemName);
    }

    public StoreNotFoundException(Long itemId) {
        super("Store", itemId);
    }
}
