package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 *  An exception to be thrown when a fridge member is not found.
 *
 * @author Trym Hamer Gudvangen
 */
public class FridgeMemberNotFoundException extends NotFoundException {

    /**
     * This method constructs a FridgeMemberNotFoundException with the specified member username.
     *
     * @param fridgeMemberName the name of the fridge that was not found
     */
    public FridgeMemberNotFoundException(String fridgeMemberName) {
        super("Fridge", fridgeMemberName);
    }

    /**
     * This method constructs a new FridgeMemberNotFoundException with the given fridge member ID that was not found.
     * @param fridgeMemberId The ID of the fridge member that was not found, given as a Long object.
     */
    public FridgeMemberNotFoundException(Long fridgeMemberId) {
        super("Fridge", fridgeMemberId);
    }

}
