package edu.ntnu.idatt2106_2023_06.backend.service.fridge;

public interface IFridgeService {

    /**
     * This method creates a new fridge and a new fridge member entry for a given user.
     * @param username  The username of the user, given as a String
     */
    void initializeFridge(String username);

    /**
     * This method creates a new fridge and a new fridge member entry for a given user.
     * @param fridgeName    The name of the fridge to be created, given as a String.
     * @param username      The username of the user, given as a String
     */
    void createFridge(String fridgeName, String username);

}
