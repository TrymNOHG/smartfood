package edu.ntnu.idatt2106_2023_06.backend.mapper.users;

import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserCreateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;

/**
 This mapper class is responsible for converting User objects to UserLoadDTO objects and vice versa.
 */
public class UserMapper {

    /**
     * Convert a UserCreateDTO object to a User object.
     *
     * @param userCreateDTO the UserCreateDTO object to be converted
     * @return a User object
     */
    public static User toUser(UserCreateDTO userCreateDTO) { //TODO: check with AuthenticationService
        return User.builder()
                .username(userCreateDTO.username())
                .password(userCreateDTO.password())
                .firstName(userCreateDTO.firstName())
                .lastName(userCreateDTO.lastName())
                .email(userCreateDTO.email())
                .build();
    }

    /**
     * Convert a User object to a UserLoadDTO object.
     *
     * @param user the User object to be converted
     * @return a UserLoadDTO object
     */
    public static UserLoadDTO userLoadDTO(User user) {
        UserLoadDTO userLoadDTO = UserLoadDTO
                .builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                //.picture(user.getPicture())
                .build();

        return userLoadDTO;
    }



}
