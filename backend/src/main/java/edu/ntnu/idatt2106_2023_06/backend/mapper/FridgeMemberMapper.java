package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.FridgeMemberId;
import edu.ntnu.idatt2106_2023_06.backend.model.User;

/**
 *  This class is a maps between the FridgeMember model and FridgeMember DTOs.
 *
 * @author Trym Hamer Gudvangen
 */
public class FridgeMemberMapper {

    /**
     * This method maps a {@link User}, a {@link Fridge}, and a boolean indicating whether the user is a superuser to
     * a {@link FridgeMember}.
     *
     * @param user        The user to map, given as a User object.
     * @param fridge      The fridge to map, given as a Fridge object.
     * @param isSuperUser Status of whether the user is a superuser in the fridge
     * @return            The mapped fridge member, given as a FridgeMember object.
     */
    public static FridgeMember toFridgeMember(User user, Fridge fridge, boolean isSuperUser) {
        return FridgeMember
                .builder()
                .id(new FridgeMemberId(user.getUserId(), fridge.getFridgeId()))
                .user(user)
                .fridge(fridge)
                .superUser(isSuperUser)
                .build();
    }

}
