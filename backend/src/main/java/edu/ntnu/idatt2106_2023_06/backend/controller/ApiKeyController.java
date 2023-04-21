package edu.ntnu.idatt2106_2023_06.backend.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  The ApiKeyController class is responsible for handling HTTP requests related to the API key.
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api-key")
@RequiredArgsConstructor
public class ApiKeyController {

    // Should be in ApiKeyConfig, but that does not work for some reason
    @Value("${kassal.api-key}")
    private String apiKey;


    /**
     * This method retrieves the API key.
     *
     * @return ResponseEntity containing the API key as its body
     */
    @GetMapping(value="/get")
    @Operation(summary = "Add user to fridge")
    public ResponseEntity<Object> getApiKey() {
        return ResponseEntity.ok(apiKey);
    }
}
