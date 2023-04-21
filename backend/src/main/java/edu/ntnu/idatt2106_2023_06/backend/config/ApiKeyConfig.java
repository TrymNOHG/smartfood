package edu.ntnu.idatt2106_2023_06.backend.config;

import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *  The ApiKeyConfig class is responsible for managing the Kassal API key. This class provides a way to access
 *  the API key, but only if the user is authenticated with a JWT token. If the user is not authenticated,
 *  a RuntimeException with message "Not authenticated" will be thrown.
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
@Component
@Configuration
@RequiredArgsConstructor
public class ApiKeyConfig {

    private JwtService jwtService;

    @Value("${kassal.api-key}")
    private String apiKey;

    /**
     * This method returns the Kassal API key if the user is authenticated. If the user is not authenticated, a
     * RuntimeException with message "Not authenticated" will be thrown.
     *
     * @return                  The Kassal API key.
     * @throws RuntimeException If the user is not authenticated.
     */
    public String getApiKey() {
        if(jwtService.isAuthenticated()) {
            return apiKey;
        }
        throw new RuntimeException("Not authenticated");
    }
}