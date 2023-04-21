package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeMemberLoadAllDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeMemberLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoadAllDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.FridgeMemberId;
import edu.ntnu.idatt2106_2023_06.backend.model.User;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * This method converts a list of user objects into a data transfer object.
     * @param user          The user to be converted, given as a User object.
     * @param isSuperUser   Status of whether the user is superuser or not.
     * @return              The data transfer object, given as a UserLoadAllDTO
     */
    public static FridgeMemberLoadDTO toFridgeMemberLoadDTO(User user, boolean isSuperUser) {
        return FridgeMemberLoadDTO
                .builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .isSuperUser(isSuperUser)
                .build();
    }

    /**
     * This method converts a list of fridge member objects into a data transfer object.
     * @param users The list of members of a fridge.
     * @return      The data transfer object, given as a UserLoadAllDTO
     */
    public static FridgeMemberLoadAllDTO toFridgeMemberLoadAllDTO(List<FridgeMember> users) {
        return FridgeMemberLoadAllDTO
                .builder()
                .memberInfo(users.
                        stream()
                        .map(member ->
                                FridgeMemberMapper.toFridgeMemberLoadDTO(member.getUser(), member.isSuperUser())
                        )
                        .collect(Collectors.toList()))
                .build();
    }

}
