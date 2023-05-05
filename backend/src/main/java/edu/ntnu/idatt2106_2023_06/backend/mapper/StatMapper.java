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

/**

 The StatMapper class contains methods for mapping between Stat DTOs and Statistics models.
 */
@RequiredArgsConstructor
public class StatMapper {

    /**
     * Converts StatDeleteFromFridgeDTO to a list of Statistics models.
     *
     * @param statDeleteFromFridgeDTO DTO containing information about deleted items
     * @param user User who deleted the items
     * @param fridge Fridge from which the items were deleted
     * @param statType1 Type of statistic to generate
     * @return List of Statistics models generated from DTO information
     */
    public static List<Statistics> toStatistics(StatDeleteFromFridgeDTO statDeleteFromFridgeDTO, User user, Fridge fridge, StatType statType1) {

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

        return statistics;
    }

    /**
     * Converts StatAddItemToFridgeDTO to a Statistics model.
     *
     * @param statAddItemToFridgeDTO DTO containing information about added items
     * @param user User who added the items
     * @param fridge Fridge to which the items were added
     * @param statType Type of statistic to generate
     * @return Statistics model generated from DTO information
     */
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