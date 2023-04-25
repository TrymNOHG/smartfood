package edu.ntnu.idatt2106_2023_06.backend.service.stat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.illegal.IllegalStatTypeException;
import edu.ntnu.idatt2106_2023_06.backend.exception.illegal.IllegalStatValueException;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.StatNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.StatMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.StatType;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.Statistics;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatTypeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService implements IStatService {

    private final Logger logger = LoggerFactory.getLogger(StatService.class);

    private final JwtService jwtService;
    private final FridgeService fridgeService;

    private final StatRepository statRepository;
    private final UserRepository userRepository;
    private final FridgeRepository fridgeRepository;
    private final StatTypeRepository statTypeRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    private void checkValidStatValue(double statValue, int statType) {
        switch (statType) {
            case 1 -> {
                if (statValue < 0 || statValue > 100) {
                    throw new IllegalStatValueException(0, 100);
                }
            }
            case 2, 3 -> {
                if (statValue < 0) {
                    throw new IllegalStatValueException();
                }
            }
            default -> throw new IllegalStatTypeException(statType);
        }
    }

    @Override
    public void statDeleteItemFromFridge(StatDeleteFromFridgeDTO statDeleteFromFridgeDTO) {
        if(!jwtService.isAuthenticated()) {
            throw new UnauthorizedException();
        }

        Long userId = jwtService.getAuthenticatedUserId();

        checkValidStatValue(statDeleteFromFridgeDTO.price(), 2);
        checkValidStatValue(statDeleteFromFridgeDTO.percentageThrown(), 1);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        Fridge fridge = fridgeRepository.findById(statDeleteFromFridgeDTO.fridgeId()).orElseThrow(
                () -> new FridgeNotFoundException(statDeleteFromFridgeDTO.fridgeId())
        );
        StatType statType1 = statTypeRepository.findById(1L).orElseThrow(
                () -> new StatNotFoundException(1L)
        );
        StatType statType2 = statTypeRepository.findById(2L).orElseThrow(
                () -> new StatNotFoundException(2L)
        );

        statRepository.saveAll(StatMapper.toStatistics(statDeleteFromFridgeDTO, user, fridge, statType1, statType2));
    }

    @Override
    public void statAddItemToFridge(StatAddItemToFridgeDTO statAddItemToFridgeDTO) {
        if(!jwtService.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        Long userId = jwtService.getAuthenticatedUserId();


        checkValidStatValue(statAddItemToFridgeDTO.price(), 3);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        Fridge fridge = fridgeRepository.findById(statAddItemToFridgeDTO.fridgeId()).orElseThrow(
                () -> new FridgeNotFoundException(statAddItemToFridgeDTO.fridgeId())
        );
        StatType statType = statTypeRepository.findById(3L).orElseThrow(
                () -> new StatNotFoundException(3L)
        );

        statRepository.save(StatMapper.toStatistics(statAddItemToFridgeDTO, user, fridge, statType));
    }

    @Override
    public String getUserStats() {
        if(!jwtService.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        Long userId = jwtService.getAuthenticatedUserId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        List<Statistics> stats = statRepository.findAllByUser(user);

        logger.info("Stats: " + stats);

        objectMapper.registerModule(new JavaTimeModule());
        String output;
        try {
            output = objectMapper.writeValueAsString(stats);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not parse stats to JSON: " + e.getMessage());
        }
        return output;
    }

    @Override
    public String getFridgeStats(Long fridgeId) {
        if(!jwtService.isAuthenticated()) {
            throw new UnauthorizedException();
        }
        Long userId = jwtService.getAuthenticatedUserId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(
                () -> new FridgeNotFoundException(fridgeId)
        );

        if(!fridgeService.userExistsInFridge(fridge.getFridgeId(), user.getUsername())) {
            throw new UnauthorizedException(user.getUsername());
        }

        List<Statistics> stats = statRepository.findAllByFridge(fridge);


        objectMapper.registerModule(new JavaTimeModule());
        String output;
        try {
            output = objectMapper.writeValueAsString(stats);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not parse stats to JSON: " + e.getMessage());
        }
        return output;
    }

}
