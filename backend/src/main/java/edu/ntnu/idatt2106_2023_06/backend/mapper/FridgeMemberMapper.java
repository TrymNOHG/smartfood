package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.FridgeMemberId;
import edu.ntnu.idatt2106_2023_06.backend.model.User;

public class FridgeMemberMapper {

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
