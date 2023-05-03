package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemMoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemSearchDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingListLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.sortAndFilter.SearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IItemService {

    Item addItem(ItemDTO itemDTO);
    void addToFridge(ItemDTO itemDTO, Long fridgeId);
    List<FridgeItemLoadDTO> getFridgeItems(Long fridgeId);
    void addToShoppingList(ItemDTO item, Long fridgeId, boolean suggestion);
    List<ShoppingListLoadDTO> getShoppingListItems(Long fridgeId);
    void removeItemFromShoppingList(ItemRemoveDTO itemRemoveDTO, boolean suggestion);
    void deleteAllItemsFromShoppingList(List<ItemRemoveDTO> itemRemoveDTOList);
    void buyItemsFromShoppingList(List<ItemMoveDTO> shoppingItemIds);
    void acceptSuggestion(ItemRemoveDTO itemDTO);
    void removeItemFromFridge(ItemRemoveDTO itemRemoveDTO);
    Page<FridgeItemLoadDTO> searchFridgeItems(FridgeItemSearchDTO fridgeItemSearchDTO);
    Page<FridgeItems> searchFridgeItems(SearchRequest request, Long fridgeId);
}