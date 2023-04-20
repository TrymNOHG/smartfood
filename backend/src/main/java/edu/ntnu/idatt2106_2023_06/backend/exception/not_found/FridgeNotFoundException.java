package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class FridgeNotFoundException extends NotFoundException {

    public FridgeNotFoundException(String fridgeName) {
        super("Fridge", fridgeName);
    }
    public FridgeNotFoundException(Long fridgeId) {
        super("Fridge", fridgeId);
    }

}
