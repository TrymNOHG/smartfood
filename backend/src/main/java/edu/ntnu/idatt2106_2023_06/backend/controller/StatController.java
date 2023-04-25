package edu.ntnu.idatt2106_2023_06.backend.controller;

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
     *
     * @param statDeleteFromFridgeDTO   The stat to add to the database.
     * @return                          A response entity with status code 200 if the stat was added successfully.
     */
    @PostMapping(value="/add/delete-item")
    @Operation(summary = "Adds a new stat to the database")
    public ResponseEntity<Object> statDeleteItem(@ParameterObject @RequestBody StatDeleteFromFridgeDTO statDeleteFromFridgeDTO,
                                                 Authentication authentication) {
        logger.info("user trying to delete item and save its stats: ");
        logger.info("item to be deleted: " + statDeleteFromFridgeDTO);
        statService.statDeleteItemFromFridge(statDeleteFromFridgeDTO);
        logger.info("ite deleted and stats saved");
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/add/bought-item")
    @Operation(summary = "Adds a new stat to the database")
    public ResponseEntity<Object> statBoughtItem(@ParameterObject @RequestBody StatAddItemToFridgeDTO statAddItemToFridgeDTO,
                                                 Authentication authentication) {
        statService.statAddItemToFridge(statAddItemToFridgeDTO);
        return ResponseEntity.ok().build();
    }
}
