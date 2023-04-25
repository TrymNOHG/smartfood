package edu.ntnu.idatt2106_2023_06.backend.config;

import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
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

    /**
     * This method initializes the database triggers. The first of which is used to respond to a user being deleted.
     * When a user is deleted, the corresponding FridgeMember is also deleted and ultimately, the fridge if possible.
     * The second trigger sets the user value to null in the stats table when deleted. The third trigger deletes
     * a fridge if the last superuser is deleted. The fourth trigger sets the fridge value to null in the stats table
     * when fridge is deleted.
     */
    @PostConstruct
    public void init() {
        userRepository.dropMemberTrigger();
        userRepository.createTriggerForDeletingMember();

        userRepository.dropUserStatTrigger();
        userRepository.createTriggerForNullingUserStat();

        fridgeRepository.dropMemberTrigger();
        fridgeRepository.createTriggerForDeletingMember();

        fridgeRepository.dropStatsTrigger();
        fridgeRepository.createTriggerForNullingFridgeStats();
    }

}
