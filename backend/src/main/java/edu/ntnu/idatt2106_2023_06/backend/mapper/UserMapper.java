package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoadAllDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserRegisterDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserSearchDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

/**
 This mapper class is responsible for converting User objects to User dDTO objects and vice versa.
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
        Logger logger = LoggerFactory.getLogger(UserMapper.class);
        logger.info("Converting user to UserLoadDTO");
        logger.info(user.getLastName() + " " + user.getFirstName() + " " + user.getEmail() + " " + user.getUsername());
        UserLoadDTO userDTO =  UserLoadDTO
                .builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                //.picture(user.getPicture())
                .build();
        logger.info("User converted to UserLoadDTO");
        return userDTO;
    }

    public static List<UserSearchDTO> userSearchDTO(List<User> user) {
        List<UserSearchDTO> userSearchDTO = new ArrayList<>();
        for(User u : user){
            userSearchDTO.add(UserSearchDTO.builder()
                    .userId(u.getUserId())
                    .username(u.getUsername())
                    .build());
        }
        return userSearchDTO;
    }

    /**
     * This method converts a list of user objects into a data transfer object.
     * @param users The list of users.
     * @return      The data transfer object, given as a UserLoadAllDTO
     */
    public static UserLoadAllDTO toUserLoadAllDTO(List<User> users) {
        return UserLoadAllDTO
                .builder()
                .users(users.
                        stream()
                        .map(UserMapper::userLoadDTO)
                        .collect(Collectors.toList()))
                .build();
    }

}
