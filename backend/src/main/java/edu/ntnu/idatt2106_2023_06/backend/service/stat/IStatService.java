package edu.ntnu.idatt2106_2023_06.backend.service.stat;

import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatBoughtItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;

public interface IStatService {
    void statDeleteItemFromFridge(StatDeleteFromFridgeDTO statDeleteFromFridgeDTO);

    void statAddItemToFridge(StatAddItemToFridgeDTO statAddItemToFridgeDTO);
}
