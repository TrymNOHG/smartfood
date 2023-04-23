package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddDTO;
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
     *
     * @param statAddDTO    The stat to add to the database.
     * @return              A response entity with status code 200 if the stat was added successfully.
     */
    @PostMapping(value="/add")
    @Operation(summary = "Adds a new stat to the database")
    public ResponseEntity<Object> addStat(@ParameterObject @RequestBody StatAddDTO statAddDTO) {
        statService.addStat(statAddDTO);
        return ResponseEntity.ok().build();
    }
}
