package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 *  An exception thrown when a shopping items is not found.
 *
 * @author Leon Egeberg Hesthaug, Trym Hamer Gudvangen
 */
public class ShoppingItemsNotFoundException extends NotFoundException {

    /**
     * This method constructs a new ShoppingItemsNotFoundException with the specified shopping items name.
     * @param shoppingItemsName The name of the shopping items not found, given as a String.
     */
    public ShoppingItemsNotFoundException(String shoppingItemsName) {
        super("ShoppingItems", shoppingItemsName);
    }

    /**
     * This method constructs a new ShoppingItemsNotFoundException with the specified shopping items id.
     * @param shoppingItemsId The id of the shopping items not found, given as a Long object.
     */
    public ShoppingItemsNotFoundException(Long shoppingItemsId) {
        super("ShoppingItems", shoppingItemsId);
    }
}
