package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.dto.ItemDTO;

import java.util.List;

public interface IItemService {


    Long addItem(ItemDTO itemDTO);
    void addToFridge(String itemName, Long fridgeId);
    List<ItemDTO> getFridgeItems(Long fridgeId);


}