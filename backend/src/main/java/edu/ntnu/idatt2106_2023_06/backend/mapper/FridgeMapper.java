package edu.ntnu.idatt2106_2023_06.backend.mapper;

import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeLoadAllDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all the mapping methods between the fridge model and DTO.
 *
 * @author Trym Hamer Gudvangen
 */
public class FridgeMapper {

    /**
     * This method converts a list of fridges to fridgeLoadDTO.
     * @param fridgeList    List of fridges
     * @return              FridgeDTO object made up of the list of fridges.
     */
    public static FridgeLoadAllDTO toFridgeLoadAllDTO(List<Fridge> fridgeList) {
        return new FridgeLoadAllDTO(fridgeList.stream()
                .map(FridgeMapper::toFridgeLoadDTO)
                .collect(Collectors.toList()));
    }

    /**
     * This method converts a fridge to a fridgeDTO.
     * @param fridge    Fridge object to be converted.
     * @return          The FridgeDTO object.
     */
    public static FridgeDTO toFridgeLoadDTO(Fridge fridge) {
        return new FridgeDTO(fridge.getFridgeId(), fridge.getFridgeName());
    }

}
