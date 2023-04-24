package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.service.stat.StatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/stat")
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    /**
     * Adds a new stat to the database. The stat is added to the fridge with the id specified in the
     * statAddDTO. The stat is also added to the user with the specified id.
     * In the StatAddDTO, the statTypeId is the id of the stat type, and the statValue is the value
     * of the stat.
     * statTypeId 1: Percentage thrown
     * statTypeId 2: Money saved
     *
     * @param statDeleteFromFridgeDTO   The stat to add to the database.
     * @return                          A response entity with status code 200 if the stat was added successfully.
     */
    @PostMapping(value="/delete-item")
    @Operation(summary = "Adds a new stat to the database")
    public ResponseEntity<Object> statDeleteItem(@ParameterObject @RequestBody StatDeleteFromFridgeDTO statDeleteFromFridgeDTO) {
        statService.statDeleteItemFromFridge(statDeleteFromFridgeDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/bought-item")
    @Operation(summary = "Adds a new stat to the database")
    public ResponseEntity<Object> statBoughtItem(@ParameterObject @RequestBody StatAddItemToFridgeDTO statAddItemToFridgeDTO) {
        statService.statAddItemToFridge(statAddItemToFridgeDTO);
        return ResponseEntity.ok().build();
    }
}
