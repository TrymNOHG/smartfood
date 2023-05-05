package edu.ntnu.idatt2106_2023_06.backend.controller;


import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 *  This class represents a REST controller for managing Kassal API keys.
 *  It provides an endpoint for retrieving the API key, which is stored in the application properties.
 *  The controller uses the JwtService to authenticate the user before returning the API key.
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

    private final JwtService jwtService;

    /**
     * This method retrieves the Kassal API key if the user is authenticated.
     * @return  ResponseEntity containing the API key if authenticated, otherwise a bad request response.
     */
    @GetMapping(value="/get")
    @Operation(summary = "Get Kassal API key")
    public ResponseEntity<Object> getApiKey() {
        if(jwtService.isAuthenticated())
            return ResponseEntity.ok(apiKey);
        return ResponseEntity.badRequest().body("Not authenticated");
    }
}
