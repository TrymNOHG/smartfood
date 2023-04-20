package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 *  Exception thrown when a fridge is not found in the system.
 *
 * @author Trym Hamer Gudvangen
 */
public class FridgeNotFoundException extends NotFoundException {

    /**
     * This method constructs a {@code FridgeNotFoundException} with the specified fridge name.
     *
     * @param fridgeName The name of the fridge that was not found, given as a String.
     */
    public FridgeNotFoundException(String fridgeName) {
        super("Fridge", fridgeName);
    }

    /**
     * Constructs a {@code FridgeNotFoundException} with the specified fridge ID.
     *
     * @param fridgeId The ID of the fridge that was not found, given as a Long object.
     */
    public FridgeNotFoundException(Long fridgeId) {
        super("Fridge", fridgeId);
    }

}
