package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;

import java.util.List;

public interface IItemService {


    Long addItem(ItemDTO itemDTO);
    void addToFridge(Long itemId, Long fridgeId, int quantity);
    List<ItemDTO> getFridgeItems(Long fridgeId);
    void addToShoppingList(Long itemId, Long fridgeId, int quantity, boolean suggestion);
    List<ItemDTO> getShoppingListItems(Long fridgeId);
    void deleteItemFromShoppingList(ItemRemoveDTO itemRemoveDTO, boolean suggestion);
    void deleteAllItemsFromShoppingList(List<ItemRemoveDTO> itemRemoveDTOList);
    void buyItemsFromShoppingList(List<ItemRemoveDTO> itemDTOList);
    void acceptSuggestion(ItemRemoveDTO itemDTO);
    void deleteItemFromFridge(ItemRemoveDTO itemRemoveDTO);


}