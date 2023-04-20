package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class FridgeMemberNotFoundException extends NotFoundException {

    public FridgeMemberNotFoundException(String fridgeName) {
        super("Fridge", fridgeName);
    }
    public FridgeMemberNotFoundException(Long fridgeId) {
        super("Fridge", fridgeId);
    }

}
