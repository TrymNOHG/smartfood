package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class ItemNotFoundException extends NotFoundException {

    public ItemNotFoundException(String itemName) {
        super("Item", itemName);
    }

    public ItemNotFoundException(Long itemId) {
        super("Item", itemId);
    }

}
