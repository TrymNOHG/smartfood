package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.StatNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.StatType;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.Statistics;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatTypeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class StatMapper {

    private final UserRepository userRepository;
    private final FridgeRepository fridgeRepository;
    private final StatTypeRepository statTypeRepository;

    public List<Statistics> toStatistics(StatDeleteFromFridgeDTO statDeleteFromFridgeDTO) {
        User user = userRepository.findById(statDeleteFromFridgeDTO.userId()).orElseThrow(
                () -> new UserNotFoundException(statDeleteFromFridgeDTO.userId())
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
        List<Statistics> statistics = new ArrayList<>();

        // Add statistics for percentage thrown
        for(int i = 0; i < statDeleteFromFridgeDTO.quantity(); i++) {
            statistics.add(Statistics.builder()
                    .user(user)
                    .fridge(fridge)
                    .timestamp(LocalDateTime.now())
                    .statValue(statDeleteFromFridgeDTO.percentageThrown())
                    .statType(statType1)
                    .storeName(statDeleteFromFridgeDTO.storeName())
                    .itemName(statDeleteFromFridgeDTO.itemName())
                    .build());
        }

        // Add statistics for item price
        for(int i = 0; i < statDeleteFromFridgeDTO.quantity(); i++) {
            statistics.add(Statistics.builder()
                    .user(user)
                    .fridge(fridge)
                    .timestamp(LocalDateTime.now())
                    .statValue(statDeleteFromFridgeDTO.price())
                    .statType(statType2)
                    .storeName(statDeleteFromFridgeDTO.storeName())
                    .itemName(statDeleteFromFridgeDTO.itemName())
                    .build());
        }

        return statistics;
    }

    public List<Statistics> toStatistics(StatAddItemToFridgeDTO statAddItemToFridgeDTO) {
        User user = userRepository.findById(statAddItemToFridgeDTO.userId()).orElseThrow(
                () -> new UserNotFoundException(statAddItemToFridgeDTO.userId())
        );
        Fridge fridge = fridgeRepository.findById(statAddItemToFridgeDTO.fridgeId()).orElseThrow(
                () -> new FridgeNotFoundException(statAddItemToFridgeDTO.fridgeId())
        );
        StatType statType = statTypeRepository.findById(3L).orElseThrow(
                () -> new StatNotFoundException(3L)
        );
        List<Statistics> statistics = new ArrayList<>();

        // Add statistics for item price
        for(int i = 0; i < statAddItemToFridgeDTO.quantity(); i++) {
            statistics.add(Statistics.builder()
                    .user(user)
                    .fridge(fridge)
                    .timestamp(LocalDateTime.now())
                    .statValue(statAddItemToFridgeDTO.price())
                    .statType(statType)
                    .storeName(statAddItemToFridgeDTO.storeName())
                    .itemName(statAddItemToFridgeDTO.itemName())
                    .build());
        }
        return statistics;
    }


}