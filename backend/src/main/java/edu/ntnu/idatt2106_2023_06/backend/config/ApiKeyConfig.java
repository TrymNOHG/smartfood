package edu.ntnu.idatt2106_2023_06.backend.config;

import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@RequiredArgsConstructor
public class ApiKeyConfig {

    private JwtService jwtService;

    @Value("${kassal.api-key}")
    private String apiKey;

    public String getApiKey() {
        if(jwtService.isAuthenticated()) {
            return apiKey;
        }
        throw new RuntimeException("Not authenticated");
    }
}