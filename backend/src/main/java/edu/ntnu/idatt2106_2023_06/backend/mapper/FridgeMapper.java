package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;

import java.util.List;

/**
 * This class contains all the mapping methods between the fridge model and DTO.
 */
public class FridgeMapper {

    /**
     * This method converts a list of fridges to fridgeLoadDTO.
     * @param fridgeList    List of fridges
     * @return              FridgeLoadDTO object made up of the list of fridges.
     */
    public static FridgeLoadDTO toFridgeLoadDTO(List<Fridge> fridgeList) {
        return new FridgeLoadDTO(fridgeList);
    }

}
