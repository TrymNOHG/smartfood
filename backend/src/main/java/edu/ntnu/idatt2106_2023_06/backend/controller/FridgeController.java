package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeUserDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final FridgeService fridgeService;
    private final Logger logger = LoggerFactory.getLogger(FridgeController.class);

    @PostMapping(value="/add/user")
    @Operation(summary = "Add user to fridge")
    public ResponseEntity<Object> addUserToFridge(@ParameterObject @RequestBody FridgeUserDTO fridgeUserDTO){
        logger.info("User wants to add a new person, " + fridgeUserDTO.username() + " to fridge");
        fridgeService.addUserToFridge(fridgeUserDTO);
        logger.info("User, " + fridgeUserDTO.username() + " has been added to fridge " + fridgeUserDTO.fridgeId() +"!");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value="/delete/user")
    @Operation(summary = "Delete user from fridge")
    public ResponseEntity<Object> deleteUserFromFridge(@ParameterObject @RequestBody FridgeUserDTO fridgeUserDTO){
        logger.info("User wants to add a new person, " + fridgeUserDTO.username() + " to fridge");
        fridgeService.addUserToFridge(fridgeUserDTO);
        logger.info("User, " + fridgeUserDTO.username() + " has been added to fridge " + fridgeUserDTO.fridgeId() +"!");
        return ResponseEntity.ok().build();
    }

}
