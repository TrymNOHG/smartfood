package edu.ntnu.idatt2106_2023_06.backend.service.users;

import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserDeletionDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserPasswordUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.exception.exists.UserExistsException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.users.UserMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 This service class handles the business logic for user-related operations.
 It implements the IUserService interface.
 It provides methods for updating and deleting users, as well as loading a user by username.
 */
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {


    /**
     * The UserRepository used to access the user database.
     */
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;


    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    /**
     * This method updates a user's information.
     * It first checks if the given user exists in the database. It then checks if the new username already exists,
     * and throws a UserExistsException if it does. It then creates a new User object with the updated information and saves
     * it to the database.
     *
     * @param userUpdateDTO A UserUpdateDTO object containing the user's current username, the new username (if any),
     *                      and other updated information.
     * @throws UserNotFoundException If the user is not found in the database.
     * @throws UserExistsException If the new username already exists in the database.
     */
    @Transactional
    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO, byte[] profilePicture){

        User user = userRepository.findByUsername(userUpdateDTO.username()).orElseThrow(() -> new UserNotFoundException(userUpdateDTO.username()));
        logger.info("User " + user.getUsername() + " was found!");
        Optional<User> newUser = userRepository.findByUsername(userUpdateDTO.newUsername());

        if(!user.getUsername().equals(userUpdateDTO.newUsername())){
            if (newUser.isPresent()) {
                logger.info("User " + user.getUsername() + " with same name found!");
                throw new UserExistsException(userUpdateDTO.newUsername());
            }
            user.setUsername(userUpdateDTO.newUsername() != null ? userUpdateDTO.username() : user.getUsername());
        }

        user.setFirstName(userUpdateDTO.firstName() != null ? userUpdateDTO.firstName() : user.getFirstName());
        user.setLastName(userUpdateDTO.lastName() != null ? userUpdateDTO.lastName() : user.getLastName());
        user.setEmail(userUpdateDTO.email() != null ? userUpdateDTO.email() : user.getEmail());
        //TODO: create picture file system

        userRepository.save(user);

    }

    /**
     * This method allows a user to update their password; however, only if the user provided the correct
     * old password. Additionally, the new password is hashed and salted using BCrypt.
     * @param userPasswordUpdateDTO Old and new password, given as a UserPasswordUpdateDTO
     * @param username              Username of the user, given as a String
     */
    @Transactional
    @Override
    public void updateUserPassword(UserPasswordUpdateDTO userPasswordUpdateDTO, String username){

        logger.info("Checking if " + username + " is a registered username");
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        logger.info("Authenticating " + username);
        authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        userPasswordUpdateDTO.oldPassword()
                )
        );

        user.setPassword(passwordEncoder.encode(userPasswordUpdateDTO.newPassword()));
        userRepository.save(user);

        logger.info("New Password Set");
    }

    /**
     Deletes a user.

     @param userDeletionDTO The DTO containing the username of the user performing the action and the username of the user to delete.
     @throws UserNotFoundException If either of the users is not found in the database.
     @throws UnauthorizedException If the user performing the action is not an admin or is trying to delete themselves.
     */
    @Transactional
    @Override
    public void deleteUser(UserDeletionDTO userDeletionDTO) {
        User user = userRepository.findByUsername(userDeletionDTO.username()).orElseThrow(() -> new UserNotFoundException(userDeletionDTO.username()));
        User userToDelete = userRepository.findByUsername(userDeletionDTO.userToDelete()).orElseThrow(() -> new UserNotFoundException(userDeletionDTO.userToDelete()));
        if(user.getUsername().equals(userToDelete.getUsername())){
            userRepository.delete(userToDelete);
        }
        else throw new UnauthorizedException(user.getUsername());
    }

    /**
     * Loads a user's DTO information.
     *
     * @param username The username of the user to load.
     * @return The DTO containing the user's information.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    @Override
    public UserLoadDTO loadUserDTOByUsername(String username) {
        return UserMapper.userLoadDTO(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }

    /**
     Loads a user's information.
     @param username The username of the user to load.
     @return The user object.
     @throws UsernameNotFoundException If the user is not found in the database.
     */
    public User loadByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
