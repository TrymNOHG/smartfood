package edu.ntnu.idatt2106_2023_06.backend.service.fridge;

import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeLoadAllDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeUserDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeMemberNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.FridgeMapper;
import edu.ntnu.idatt2106_2023_06.backend.mapper.FridgeMemberMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.*;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeMemberRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  This service class handles the business logic for fridge-related operations.
 *  It implements the IFridgeService interface.
 *  It provides methods for updating and deleting users from a fridge, as well as authorization of these actions.
 *
 * @author Trym Hamer Gudvangen
 */
@Service
@RequiredArgsConstructor
public class FridgeService implements IFridgeService{

    private final FridgeRepository fridgeRepository;
    private final FridgeMemberRepository fridgeMemberRepository;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(FridgeService.class);




    /**
     * This method creates a new fridge and a new fridge member entry for a given user.
     * @param username  The username of the user, given as a String
     */
    @Override
    @Transactional
    public void initializeFridge(String username) {
        createFridge("Home Fridge", username);
    }

    /**
     * This method creates a new fridge and a new fridge member entry for a given user.
     * @param fridgeName    The name of the fridge to be created, given as a String.
     * @param username      The username of the user, given as a String
     */
    @Override
    @Transactional
    public void createFridge(String fridgeName, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Fridge savedFridge = fridgeRepository.save(new Fridge(fridgeName));

        fridgeMemberRepository.save(FridgeMemberMapper.toFridgeMember(user, savedFridge, true));
    }

    /**
     * This method updates fridge's name. However, this is only possible if the person trying to update
     * the user is already a part of the fridge and is a superuser.
     * @param fridgeDTO          The information surrounding the fridge, given as a FridgeDTO object.
     * @param userTryingToUpdate The username of the user trying to update the other user, given as a String
     */
    @Transactional
    public void updateFridgeName(FridgeDTO fridgeDTO, String userTryingToUpdate) {

        authorizeFridgeMemberAction(fridgeDTO.fridgeId(), userTryingToUpdate, "update");


        logger.info("Attempting to change the name of the fridge.");
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeDTO.fridgeId())
                .orElseThrow(() -> new FridgeNotFoundException(fridgeDTO.fridgeId()));

        fridge.setFridgeName(fridgeDTO.fridgeName());

        fridgeRepository.save(fridge);

        logger.info("Fridge name has been successfully changed.");
    }

    /**
     * This method adds a user to a given fridge. However, this is only possible if the person trying to add
     * the user is already a part of the fridge and is a superuser.
     * @param fridgeUserDTO     The information surrounding the user and fridge, given as a FridgeUserDTO object.
     * @param userTryingToAdd   The username of the user trying to add the other user, given as a String
     */
    @Transactional
    public void addUserToFridge(FridgeUserDTO fridgeUserDTO, String userTryingToAdd) {

        authorizeFridgeMemberAction(fridgeUserDTO.fridgeId(), userTryingToAdd, "add");

        User userToBeAdded = userRepository.findByUsername(fridgeUserDTO.username())
                .orElseThrow(() -> new UsernameNotFoundException(fridgeUserDTO.username()));

        Fridge fridgeToBeAddedTo = fridgeRepository.findByFridgeId(fridgeUserDTO.fridgeId())
                .orElseThrow(() -> new FridgeNotFoundException(fridgeUserDTO.fridgeId()));

        fridgeMemberRepository.save(FridgeMemberMapper.toFridgeMember(
                userToBeAdded, fridgeToBeAddedTo, fridgeUserDTO.isSuperUser())
        );
    }

    /**
     * This method removes a user from a given fridge. However, this is only possible if the person trying to remove
     * the user is already a part of the fridge and is a superuser.
     * @param fridgeUserDTO     The information surrounding the user and fridge, given as a FridgeUserDTO object.
     * @param userTryingToRemove   The username of the user trying to remove the other user, given as a String
     */
    @Transactional
    public void deleteUserFromFridge(FridgeUserDTO fridgeUserDTO, String userTryingToRemove) {

        if(!userExistsInFridge(fridgeUserDTO.fridgeId(), fridgeUserDTO.username())) return;

        logger.info("Checking if the user wanting to remove is the same as the one being removed");
        if(!fridgeUserDTO.username().equals(userTryingToRemove)) {
            authorizeFridgeMemberAction(fridgeUserDTO.fridgeId(), userTryingToRemove, "remove");
        }
        else logger.info("User is trying to remove themselves.");

        fridgeMemberRepository.deleteFridgeMemberByFridge_FridgeIdAndUser_Username(fridgeUserDTO.fridgeId(), fridgeUserDTO.username());

        logger.info("User " + fridgeUserDTO.username() + " is no longer in the fridge with id: " + fridgeUserDTO.fridgeId() +"!");

    }

    /**
     * This method updates a user from a given fridge. However, this is only possible if the person trying to update
     * the user is already a part of the fridge and is a superuser.
     * @param fridgeUserDTO      The information surrounding the user and fridge, given as a FridgeUserDTO object.
     * @param userTryingToUpdate The username of the user trying to update the other user, given as a String
     */
    @Transactional
    public void updateUserFromFridge(FridgeUserDTO fridgeUserDTO, String userTryingToUpdate) {

        authorizeFridgeMemberAction(fridgeUserDTO.fridgeId(), userTryingToUpdate, "update");

        FridgeMember fridgeMemberToBeUpdated = fridgeMemberRepository
                .findFridgeMemberByFridge_FridgeIdAndUser_Username(fridgeUserDTO.fridgeId(),
                        fridgeUserDTO.username())
                        .orElseThrow(() -> new FridgeNotFoundException(fridgeUserDTO.fridgeId()));


        fridgeMemberToBeUpdated.setSuperUser(fridgeUserDTO.isSuperUser());

        fridgeMemberRepository.save(fridgeMemberToBeUpdated);

        logger.info(String.format(fridgeUserDTO.username() + " was changed %s super user.",
                fridgeUserDTO.isSuperUser() ? "to" : "from"));
    }

    /**
     * This method retrieves all the fridge ids for a given user.
     * @param username  The username of the user, given as a String.
     * @return          List of fridge ids.
     */
    public List<Long> retrieveFridgeIdsByUsername(String username) {
        logger.info("Retrieving fridge ids for " + username);

        return fridgeMemberRepository.findFridgeMembersByUser_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException(username))
                .stream().map(fridgeMember -> fridgeMember.getFridge().getFridgeId())
                .collect(Collectors.toList());
    }

    /**
     * This method retrieves all the fridge ids for a given user.
     * @param username  The username of the user, given as a String.
     * @return          FridgeDTO containing the list of Fridge objects
     */
    public FridgeLoadAllDTO retrieveFridgesByUsername(String username) {
        logger.info("Retrieving fridges for " + username);

        return FridgeMapper.toFridgeLoadAllDTO(fridgeMemberRepository.findFridgeMembersByUser_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException(username))
                .stream().map(FridgeMember::getFridge)
                .collect(Collectors.toList()));
    }



    /**
     * This method checks whether the user trying to perform the action on the fridge member is authorized.
     *
     * @param fridgeId              The id of the fridge, given as a Long object.
     * @param userPerformingAction  The username of the user performing the action, given as a String.
     * @param action                A verb describing the action, given as a String.
     */
    private void  authorizeFridgeMemberAction(Long fridgeId, String userPerformingAction, String action) {

        logger.info(String.format("Checking that the user trying to %s is a super user and a member of the fridge", action));
        if(!fridgeMemberRepository
                .findFridgeMemberByFridge_FridgeIdAndUser_Username(fridgeId, userPerformingAction)
                .orElseThrow(() -> new FridgeMemberNotFoundException(userPerformingAction))
                .isSuperUser()) {
            logger.warn("User is not authorized since they are not a super user!");
            throw new UnauthorizedException(userPerformingAction, "This user is part of the fridge but not a super user.");
        }
        logger.info("User is super user and part of the fridge!");
    }

    /**
     * This method checks whether the effected user exists.
     * @param fridgeId  The id of the fridge, given as a Long object.
     * @param username  The username of the user the action is being performed on, given as a String.
     * @return          Status of whether user exists in fridge, {@code true}, or not, {@code false}
     */
    private boolean userExistsInFridge(Long fridgeId, String username) {
        logger.info("Checking whether the soon-to-be effected user is a part of the fridge");
        if(fridgeMemberRepository.existsFridgeMemberByFridge_FridgeIdAndUser_Username(fridgeId, username)) {
            logger.warn("User exists!!!");
            return true;
        }
        return false;
    }

}
