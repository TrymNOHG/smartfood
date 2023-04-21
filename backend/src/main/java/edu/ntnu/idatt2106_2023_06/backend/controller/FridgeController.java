package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeLoadAllDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeUserDTO;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  The FridgeController class provides API endpoints for managing fridge users.
 *  It uses the FridgeService to handle the logic of adding and deleting
 *  fridge users.
 *
 * @author Trym Hamer Gudvangen
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final FridgeService fridgeService;
    private final Logger logger = LoggerFactory.getLogger(FridgeController.class);


    /**
     * This endpoint serves to add a User to a fridge.
     *
     * @param fridgeUserDTO     The FridgeUserDTO containing the details of the user to be added to the fridge.
     * @param authentication    The Authentication object of the user making the request.
     * @return                  A ResponseEntity indicating whether the operation was successful.
     */
    @PostMapping(value="/add/user")
    @Operation(summary = "Add user to fridge")
    public ResponseEntity<Object> addUserToFridge(@ParameterObject @RequestBody FridgeUserDTO fridgeUserDTO,
                                                  Authentication authentication){
        logger.info("User, " + authentication.getName() + " wants to add a new person, " + fridgeUserDTO.username()
                + " to fridge");
        fridgeService.addUserToFridge(fridgeUserDTO, authentication.getName());
        logger.info("User, " + fridgeUserDTO.username() + " has been added to fridge " + fridgeUserDTO.fridgeId() +"!");
        return ResponseEntity.ok().build();
    }

    /**
     * This endpoint serves to remove a User from a fridge.
     *
     * @param fridgeUserDTO     The FridgeUserDTO containing the details of the user to be deleted from the fridge.
     * @param authentication    The Authentication object of the user making the request.
     * @return                  A ResponseEntity indicating whether the operation was successful.
     */
    @DeleteMapping(value="/delete/user")
    @Operation(summary = "Delete user from fridge")
    public ResponseEntity<Object> deleteUserFromFridge(@ParameterObject @RequestBody FridgeUserDTO fridgeUserDTO,
                                                       Authentication authentication){
        logger.info("bruh: " + fridgeUserDTO.toString());
        logger.info("User wants to delete the user, " + fridgeUserDTO.username() + ", from the fridge");
        fridgeService.deleteUserFromFridge(fridgeUserDTO, authentication.getName());
        return ResponseEntity.ok().build();
    }

    /**
     * This endpoint serves to update a User from a fridge.
     *
     * @param fridgeUserDTO     The FridgeUserDTO containing the details of the user to be updated from the fridge.
     * @param authentication    The Authentication object of the user making the request.
     * @return                  A ResponseEntity indicating whether the operation was successful.
     */
    @PutMapping(value="/update/user")
    @Operation(summary = "Update user from fridge")
    public ResponseEntity<Object> updateUserFromFridge(@ParameterObject @RequestBody FridgeUserDTO fridgeUserDTO,
                                                       Authentication authentication){
        logger.info("User, " + authentication.getName() + " wants to update the user, " + fridgeUserDTO.username() + ", from the fridge");
        fridgeService.updateUserFromFridge(fridgeUserDTO, authentication.getName());
        return ResponseEntity.ok().build();
    }

    /**
     * This endpoint retrieves all the fridge ids for a given user.
     * @param username  The username of the user, given as a String.
     * @return          Response entity containing the fridge ids.
     */
    @GetMapping(value = "/loadAllId")
    @Operation(summary = "Load all fridge ids for a given user.")
    public ResponseEntity<List<Long>> loadFridgeIdsByUser(@ParameterObject @RequestParam String username) {
        List<Long> fridgeIds = fridgeService.retrieveFridgeIdsByUsername(username);
        logger.info("All of the fridge ids for " + username + " have been retrieved.");
        return ResponseEntity.ok(fridgeIds);
    }

    /**
     * This endpoint retrieves all the fridges for a given user.
     * @param username  The username of the user, given as a String.
     * @return          Response entity containing a FridgeDTO.
     */
    @GetMapping(value = "/loadAll")
    @Operation(summary = "Load all fridge ids for a given user.")
    public ResponseEntity<FridgeLoadAllDTO> loadFridgesByUser(@ParameterObject @RequestParam(name = "user") String username) {
        FridgeLoadAllDTO fridgeLoadDTO = fridgeService.retrieveFridgesByUsername(username);
        logger.info("All of the fridge ids for " + username + " have been retrieved.");
        return ResponseEntity.ok(fridgeLoadDTO);
    }

    /**
     * This endpoint allows an authenticated user to create a new fridge.
     * @param fridgeName        Name of the fridge, given as a String.
     * @param authentication    The authentication of the HTTP Request sender, given as an Authentication object.
     * @return                  Response entity containing the HTTP status.
     */
    @PostMapping(value = "/create")
    @Operation(summary = "Load all fridge ids for a given user.")
    public ResponseEntity<Object> createFridge(@ParameterObject @RequestParam(name = "fridgeName") String fridgeName,
                                               Authentication authentication) {

        logger.info("Attempting to create fridge for user " + authentication.getName());
        fridgeService.createFridge(fridgeName, authentication.getName());
        logger.info("Fridge and fridge member was successfully created!");
        return ResponseEntity.ok().build();
    }

    /**
     * This endpoint serves to update the fridge name.
     *
     * @param fridgeDTO         New name of the fridge, given as String.
     * @param authentication    The Authentication object of the user making the request.
     * @return                  A ResponseEntity indicating whether the operation was successful.
     */
    @PutMapping(value="/update")
    @Operation(summary = "Update fridge name")
    public ResponseEntity<Object> updateFridgeName(@ParameterObject @RequestBody FridgeDTO fridgeDTO,
                                                   Authentication authentication){
        logger.info("User, " + authentication.getName() + " wants to update the fridge name");
        fridgeService.updateFridgeName(fridgeDTO, authentication.getName());
        return ResponseEntity.ok().build();
    }

}
