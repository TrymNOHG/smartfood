package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.service.stat.StatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);


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
        logger.info("user trying to delete item and save its stats: ");
        logger.info("item to be deleted: " + statDeleteFromFridgeDTO);
        statService.statDeleteItemFromFridge(statDeleteFromFridgeDTO);
        logger.info("ite deleted and stats saved");
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
        logger.info("user trying to buy item and save its stats: ");
        logger.info("item to be bought: " + statAddItemToFridgeDTO);
        statService.statAddItemToFridge(statAddItemToFridgeDTO);
        logger.info("item bought and stats saved");
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

    /**
     * Get the average percentage of items thrown per day by the user.
     *
     * @param authentication    The JWT token of the user.
     */
    @GetMapping(value="/get/user-stats/avg-thrown-per-day")
    @Operation(summary = "Get the average percentage of items thrown per day by the user")
    public ResponseEntity<Object> getUserAvgThrownPerDay(Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.ok(statService.getAverageThrownPerDayUser());
    }

    /**
     * Get the average percentage of items thrown per day in the fridge.
     *
     * @param authentication    The JWT token of the user.
     */
    @GetMapping(value="/get/fridge-stats/avg-thrown-per-day/{fridgeId}")
    @Operation(summary = "Get the average percentage of items thrown per day by users in a fridge")
    public ResponseEntity<Object> getFridgeAvgThrownPerDay(@PathVariable Long fridgeId,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.ok(statService.getAverageThrownPerDayFridge(fridgeId));
    }

    /**
     * Get the money wasted per day by the user.
     *
     * @param authentication    The JWT token of the user.
     */
    @GetMapping(value="/get/user-stats/money-wasted-per-day")
    @Operation(summary = "Get the money thrown away per day by the user")
    public ResponseEntity<Object> getUserMoneyWastedPerDay(Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.ok(statService.getMoneyWastedPerDayUser());
    }

    /**
     * Get the money wasted in per dey in the fridge.
     *
     * @param authentication    The JWT token of the user.
     * @param fridgeId          The id of the fridge.
     */
    @GetMapping(value="/get/fridge-stats/avg-thrown-per-day/{fridgeId}")
    @Operation(summary = "Get the average percentage of items thrown per day by users in a fridge")
    public ResponseEntity<Object> getFridgeMoneyWastedPerDay(@PathVariable Long fridgeId,
                                                           Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.ok(statService.getMoneyWastedPerDayFridge(fridgeId));
    }

}
