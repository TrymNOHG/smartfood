package edu.ntnu.idatt2106_2023_06.backend.service.fridge;

import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeMemberLoadAllDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoadAllDTO;

/**
 * This interface contains the methods that the fridge service needs to implement.
 *
 * @author Trym Hamer Gudvangen
 */
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

    /**
     * This method retrieves all the users for a given fridge.
     * @param fridgeId  The id of the fridge, given as a Long object.
     * @return          FridgeMemberLoadAllDTO containing a list of all the members.
     */
    FridgeMemberLoadAllDTO retrieveMembersByFridgeId(Long fridgeId, String username);

}
