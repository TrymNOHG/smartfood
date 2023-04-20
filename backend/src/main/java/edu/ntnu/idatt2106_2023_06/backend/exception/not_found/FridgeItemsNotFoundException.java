package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class FridgeItemsNotFoundException extends NotFoundException {

    public FridgeItemsNotFoundException(String fridgeName) {
        super("FridgeItems", fridgeName);
    }
    public FridgeItemsNotFoundException(Long fridgeId) {
        super("FridgeItems", fridgeId);
    }
}
