package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserRegisterDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.User;

/**
 This mapper class is responsible for converting User objects to UserLoadDTO objects and vice versa.
 */
public class UserMapper {

    /**
     * Convert a UserCreateDTO object to a User object.
     *
     * @param userRegisterDTO the UserCreateDTO object to be converted
     * @return a User object
     */
    public static User toUser(UserRegisterDTO userRegisterDTO) { //TODO: check with AuthenticationService
        return User.builder()
                .username(userRegisterDTO.username())
                .password(userRegisterDTO.password())
                .firstName(userRegisterDTO.firstName())
                .lastName(userRegisterDTO.lastName())
                .email(userRegisterDTO.email())
                .build();
    }

    /**
     * Convert a User object to a UserLoadDTO object.
     *
     * @param user the User object to be converted
     * @return a UserLoadDTO object
     */
    public static UserLoadDTO userLoadDTO(User user) {
        return UserLoadDTO
                .builder()
                //.userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                //.picture(user.getPicture())
                .build();
    }

}
