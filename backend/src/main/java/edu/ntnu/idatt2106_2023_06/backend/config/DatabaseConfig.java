package edu.ntnu.idatt2106_2023_06.backend.config;

import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *  This is a Configuration class for Database Initialization.
 */
@Configuration
@RequiredArgsConstructor
@Profile("!test")
public class DatabaseConfig {

    private final UserRepository userRepository;
    private final FridgeRepository fridgeRepository;

    @PostConstruct
    public void init() {
        userRepository.dropTrigger();
        userRepository.createTrigger();

        fridgeRepository.dropTrigger();
        fridgeRepository.createTrigger();
    }

}
