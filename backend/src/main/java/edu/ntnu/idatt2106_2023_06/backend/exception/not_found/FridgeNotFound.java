package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class FridgeNotFound extends NotFoundException {

    public FridgeNotFound(String fridgeName) {
        super("Fridge", fridgeName);
    }
    public FridgeNotFound(Long fridgeId) {
        super("Fridge", fridgeId);
    }

}
