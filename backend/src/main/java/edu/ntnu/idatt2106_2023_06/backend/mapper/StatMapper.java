package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.StatType;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.Statistics;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class StatMapper {

    public static List<Statistics> toStatistics(StatDeleteFromFridgeDTO statDeleteFromFridgeDTO, User user, Fridge fridge, StatType statType1, StatType statType2) {

        List<Statistics> statistics = new ArrayList<>();

        // Add statistics for percentage thrown
        statistics.add(Statistics.builder()
                .user(user)
                .fridge(fridge)
                .timestamp(LocalDateTime.now())
                .statValue(statDeleteFromFridgeDTO.amountDeleted().doubleValue())
                .statType(statType1)
                .storeName(statDeleteFromFridgeDTO.storeName())
                .itemName(statDeleteFromFridgeDTO.itemName())
                .build());


        // Add statistics for item price
        statistics.add(Statistics.builder()
                .user(user)
                .fridge(fridge)
                .timestamp(LocalDateTime.now())
                .statType(statType2)
                .storeName(statDeleteFromFridgeDTO.storeName())
                .itemName(statDeleteFromFridgeDTO.itemName())
                .build());


        return statistics;
    }

    public static Statistics toStatistics(StatAddItemToFridgeDTO statAddItemToFridgeDTO, User user, Fridge fridge, StatType statType) {

        // Add statistics for item price


        return Statistics.builder()
                .user(user)
                .fridge(fridge)
                .timestamp(LocalDateTime.now())
                .statValue(statAddItemToFridgeDTO.price())
                .statType(statType)
                .storeName(statAddItemToFridgeDTO.storeName())
                .itemName(statAddItemToFridgeDTO.itemName())
                .build();
    }


}