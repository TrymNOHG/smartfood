package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 * Exception thrown when a fridge's items cannot be found.
 *
 * @author Leon Egeberg Hesthaug, Trym Hamer Gudvangen
 */
public class FridgeItemsNotFoundException extends NotFoundException {

    /**
     * This method constructs a new FridgeItemsNotFoundException with the given fridge name.
     *
     * @param fridgeName The name of the fridge, given as a String
     */
    public FridgeItemsNotFoundException(String fridgeName) {
        super("FridgeItems", fridgeName);
    }

    /**
     * This method constructs a new FridgeItemsNotFoundException with the given fridge ID.
     *
     * @param fridgeId The ID of the fridge, given as a Long
     */
    public FridgeItemsNotFoundException(Long fridgeId) {
        super("FridgeItems", fridgeId);
    }
}
