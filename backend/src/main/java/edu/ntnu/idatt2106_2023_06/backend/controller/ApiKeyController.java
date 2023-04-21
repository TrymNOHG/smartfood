package edu.ntnu.idatt2106_2023_06.backend.controller;


import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
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

    private final JwtService jwtService;

    @GetMapping(value="/get")
    @Operation(summary = "Add user to fridge")
    public ResponseEntity<Object> getApiKey() {
        if(jwtService.isAuthenticated())
            return ResponseEntity.ok(apiKey);
        return ResponseEntity.badRequest().body("Not authenticated");
    }
}
