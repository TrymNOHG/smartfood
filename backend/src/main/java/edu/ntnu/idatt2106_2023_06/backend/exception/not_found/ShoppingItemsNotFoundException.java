package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class ShoppingItemsNotFoundException extends NotFoundException {

    public ShoppingItemsNotFoundException(String fridgeName) {
        super("ShoppingItems", fridgeName);
    }
    public ShoppingItemsNotFoundException(Long fridgeId) {
        super("ShoppingItems", fridgeId);
    }
}
