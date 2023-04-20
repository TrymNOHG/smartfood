package edu.ntnu.idatt2106_2023_06.backend.controller;


import edu.ntnu.idatt2106_2023_06.backend.config.ApiKeyConfig;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api-key")
@RequiredArgsConstructor
public class ApiKeyController {

    // Should be in ApiKeyConfig, but that does not work for some reason
    @Value("${kassal.api-key}")
    private String apiKey;

    @GetMapping(value="/get")
    @Operation(summary = "Add user to fridge")
    public ResponseEntity<Object> getApiKey() {
        return ResponseEntity.ok(apiKey);
    }
}
