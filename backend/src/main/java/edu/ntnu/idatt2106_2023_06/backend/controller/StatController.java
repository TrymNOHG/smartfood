package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.service.stat.StatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/stat")
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    /**
     * Adds a new stat to the database. The stat is connected to the user automatically with the help of the JWT token.
     * This endpoint should be used when a user deletes an item from their fridge.
     *
     * @param statDeleteFromFridgeDTO   The stat to add to the database.
     * @return                          A response entity with status code 200 if the stat was added successfully.
     */
    @PostMapping(value="/add/delete-item")
    @Operation(summary = "Adds a new stat to the database", description = "This endpoint should be used when a user deletes an item from their fridge.")
    public ResponseEntity<Object> statDeleteItem(@ParameterObject @RequestBody StatDeleteFromFridgeDTO statDeleteFromFridgeDTO,
                                                 Authentication authentication) {
        statService.statDeleteItemFromFridge(statDeleteFromFridgeDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Adds a new stat to the database. The stat is connected to the user automatically with the help of the JWT token.
     * This endpoint should be used when a user buys an item.
     *
     * @param statAddItemToFridgeDTO    The stat to add to the database.
     * @param authentication            The JWT token of the user.
     * @return          A response entity with status code 200 if the stat was added successfully.
     */
    @PostMapping(value="/add/bought-item")
    @Operation(summary = "Adds a new stat to the database", description = "This endpoint should be used when a user buys an item from a store.")
    public ResponseEntity<Object> statBoughtItem(@ParameterObject @RequestBody StatAddItemToFridgeDTO statAddItemToFridgeDTO,
                                                 Authentication authentication) {
        statService.statAddItemToFridge(statAddItemToFridgeDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Get the personal statistics of the user.
     *
     * @param authentication    The JWT token of the user.
     */
    @GetMapping(value="/get/user-stats")
    @Operation(summary = "Get the personal statistics of the user")
    public ResponseEntity<Object> getUserStats(Authentication authentication) {
        return ResponseEntity.ok(statService.getUserStats());
    }

    /**
     * Get the statistics of a fridge.
     *
     * @param fridgeId          The id of the fridge.
     * @param authentication    The JWT token of the user.
     */
    @GetMapping(value="/get/fridge-stats/{fridgeId}")
    @Operation(summary = "Get the statistics of a fridge")
    public ResponseEntity<Object> getFridgeStats(@PathVariable Long fridgeId,
                                                 Authentication authentication) {
        return ResponseEntity.ok(statService.getFridgeStats(fridgeId));
    }
}
