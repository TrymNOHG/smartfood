package edu.ntnu.idatt2106_2023_06.backend.service.stat;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import org.springframework.security.core.Authentication;

public interface IStatService {
    void statDeleteItemFromFridge(StatDeleteFromFridgeDTO statDeleteFromFridgeDTO);

    void statAddItemToFridge(StatAddItemToFridgeDTO statAddItemToFridgeDTO);

    String getUserStats() throws JsonProcessingException;

    String getFridgeStats(Long fridgeId);

    Object getAverageThrownPerDayUser() throws JsonProcessingException;

    String getAverageThrownPerDayFridge(long fridgeId) throws JsonProcessingException;

    Double getAverageThrownTotalUser() throws JsonProcessingException;

    Double getAverageThrownTotalFridge(long fridgeId) throws JsonProcessingException;

    String getMoneyWastedPerDayUser() throws JsonProcessingException;

    String getMoneyWastedPerDayFridge(Long fridgeId) throws JsonProcessingException;

    String getMoneyUsedPerDayUser() throws JsonProcessingException;

    String getMoneyUsedPerDayFridge(Long fridgeId) throws JsonProcessingException;
}
