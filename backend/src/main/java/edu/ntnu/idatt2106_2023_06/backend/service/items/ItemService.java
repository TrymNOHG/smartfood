package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.*;
import edu.ntnu.idatt2106_2023_06.backend.mapper.ItemMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.*;
import edu.ntnu.idatt2106_2023_06.backend.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**

 ItemService is responsible for handling requests related to items, including adding items to the store,
 fridge and shopping list. It also has methods to retrieve items from the fridge and shopping list and delete
 them from the fridge.
 */
@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {

    private final ItemRepository itemRepository;
    private final FridgeItemsRepository fridgeItemsRepository;
    private final FridgeRepository fridgeRepository;
    private final StoreRepository storeRepository;
    private final ShoppingItemsRepository shoppingItemsRepository;

    /**
     * Adds an item to the item database.
     *
     * @param itemDTO The ItemDTO containing the item to add.
     * @return The ID of the added item.
     * @throws StoreNotFoundException if the store cannot be found.
     * @throws ItemNotFoundException if the item cannot be found.
     */
    @Override
    public Long addItem(ItemDTO itemDTO) {
        if (itemDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");
        Store store = storeRepository.findByStoreName(itemDTO.store()).orElse(null);
        if (store == null){
            store = Store.builder()
                    .storeName(itemDTO.store())
                    .itemsInStore(new ArrayList<>())
                    .build();
            storeRepository.save(store);
        }

        store = storeRepository.findByStoreName(itemDTO.store()).orElseThrow(() -> new StoreNotFoundException(itemDTO.store()));
        Item item = itemRepository.findByProductNameAndStore(itemDTO.name(), store).orElse(null);
        if (item != null) return item.getItemId();

        Item i = ItemMapper.toItem(itemDTO, store);
        itemRepository.save(i);

        item = itemRepository.findByProductNameAndStore(itemDTO.name(), store).orElseThrow(() -> new ItemNotFoundException(itemDTO.name()));
        return item.getItemId();
    }

    /**
     * Adds an item to the fridge.
     *
     * @param itemId The ID of the item to add.
     * @param fridgeId The ID of the fridge to add the item to.
     * @param quantity The quantity of the item to add.
     * @throws ItemNotFoundException if the item cannot be found.
     * @throws FridgeNotFoundException if the fridge cannot be found.
     */
    @Override
    public void addToFridge(Long itemId, Long fridgeId, int quantity) {
        if (quantity <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");
        Item item = itemRepository.findByItemId(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        FridgeItems fridgeItem = fridgeItemsRepository.findByItemAndFridge(item, fridge).orElse(null);
        if(fridgeItem == null){
             fridgeItem = FridgeItems.builder()
                     .id(new FridgeItemsId(item.getItemId(), fridge.getFridgeId()))
                    .item(item)
                    .fridge(fridge)
                    .quantity(quantity)
                    .build();
        }
        else {
            fridgeItem.setQuantity(fridgeItem.getQuantity() + quantity);
        }
        fridgeItemsRepository.save(fridgeItem);
    }

    /**
     * Retrieves a list of items from the specified fridge.
     *
     * @param fridgeId The ID of the fridge to retrieve items from.
     * @return A list of ItemDTO objects representing the items in the fridge.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws FridgeItemsNotFoundException if there are no items in the specified fridge.
     */
    @Override
    public List<ItemDTO> getFridgeItems(Long fridgeId) {
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        List<FridgeItems> fridgeItems = fridgeItemsRepository.findByFridge(fridge).orElseThrow(() -> new FridgeItemsNotFoundException(fridgeId));
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (FridgeItems item : fridgeItems){
            itemDTOList.add(ItemMapper.toItemDTO(item.getItem(), item.getQuantity()));
        }
        return itemDTOList;
    }

    /**
     * Deletes the specified quantity of an item from the specified fridge.
     *
     * @param itemRemoveDTO A DTO object containing the details of the item to remove.
     * @throws StoreNotFoundException if the specified store name does not exist.
     * @throws ItemNotFoundException if the specified item name does not exist in the specified store.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws FridgeItemsNotFoundException if the specified item does not exist in the specified fridge.
     */
    @Override
    public void deleteItemFromFridge(ItemRemoveDTO itemRemoveDTO) {
        if (itemRemoveDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");
        Store store = storeRepository.findByStoreName(itemRemoveDTO.store()).orElseThrow(() -> new StoreNotFoundException(itemRemoveDTO.store()));
        Item item = itemRepository.findByProductNameAndStore(itemRemoveDTO.itemName(), store).orElseThrow(() -> new ItemNotFoundException(itemRemoveDTO.itemName()));
        Fridge fridge = fridgeRepository.findByFridgeId(itemRemoveDTO.fridgeId()).orElseThrow(() -> new FridgeNotFoundException(itemRemoveDTO.fridgeId()));
        FridgeItems fridgeItem = fridgeItemsRepository.findByItemAndFridge(item, fridge).orElseThrow(() -> new FridgeItemsNotFoundException(""));
        if (fridgeItem.getQuantity() <= itemRemoveDTO.quantity()){
            fridgeItemsRepository.delete(fridgeItem);
        }
        else {
            fridgeItem.setQuantity(fridgeItem.getQuantity() - itemRemoveDTO.quantity());
            fridgeItemsRepository.save(fridgeItem);
        }
    }

    /**
     * Adds an item to the shopping list for the specified fridge.
     *
     * @param itemName The ID of the item to add to the shopping list.
     * @param fridgeId The ID of the fridge to add the item to the shopping list for.
     * @param quantity The quantity of the item to add to the shopping list.
     * @param suggestion A boolean indicating whether the item is a suggested item or not.
     * @throws ItemNotFoundException if the specified item ID does not exist.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     */
    @Override
    public void addToShoppingList(Long itemName, Long fridgeId, int quantity, boolean suggestion) {
        if (quantity <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");
        Item item = itemRepository.findByItemId(itemName).orElseThrow(() -> new ItemNotFoundException(itemName));
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        ShoppingItems shoppingItem = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, suggestion).orElse(null);
        if(shoppingItem == null){
            shoppingItem = ShoppingItems.builder()
                    .id(new FridgeItemsId(item.getItemId(), fridge.getFridgeId()))
                    .item(item)
                    .fridge(fridge)
                    .quantity(quantity)
                    .suggestion(suggestion)
                    .build();
        }
        else {
            shoppingItem.setQuantity(shoppingItem.getQuantity() + quantity);
        }
        shoppingItemsRepository.save(shoppingItem);
    }

    /**
     * Retrieves a list of items from the shopping list for the specified fridge.
     *
     * @param fridgeId The ID of the fridge to retrieve shopping list items for.
     * @return A list of ItemDTO objects representing the items in the shopping list for the specified fridge.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws ShoppingItemsNotFoundException if there are no items in the shopping list for the specified fridge.
     */
    @Override
    public List<ItemDTO> getShoppingListItems(Long fridgeId) {
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        List<ShoppingItems> shoppingItems = shoppingItemsRepository.findByFridge(fridge).orElseThrow(() -> new ShoppingItemsNotFoundException(fridgeId));
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (ShoppingItems item : shoppingItems){
            itemDTOList.add(ItemMapper.toItemDTO(item.getItem(), item.getQuantity()));
        }
        return itemDTOList;
    }

    /**
     * Deletes the specified quantity of an item from the shopping list for the specified fridge.
     *
     * @param itemRemoveDTO A DTO object containing the details of the item to remove.
     * @param suggestion A boolean indicating whether the item is a suggested item or not.
     * @throws StoreNotFoundException if the specified store name does not exist.
     * @throws ItemNotFoundException if the specified item name does not exist in the specified store.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws ShoppingItemsNotFoundException if the specified item does not exist in the shopping list for the specified fridge.
     */
    @Override
    public void deleteItemFromShoppingList(ItemRemoveDTO itemRemoveDTO, boolean suggestion) {
        if (itemRemoveDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");
        Store store = storeRepository.findByStoreName(itemRemoveDTO.store()).orElseThrow(() -> new StoreNotFoundException(itemRemoveDTO.store()));
        Item item = itemRepository.findByProductNameAndStore(itemRemoveDTO.itemName(), store).orElseThrow(() -> new ItemNotFoundException(itemRemoveDTO.itemName()));
        Fridge fridge = fridgeRepository.findByFridgeId(itemRemoveDTO.fridgeId()).orElseThrow(() -> new FridgeNotFoundException(itemRemoveDTO.fridgeId()));
        ShoppingItems shoppingItem = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, suggestion).orElseThrow(() -> new ShoppingItemsNotFoundException(""));
        if (shoppingItem.getQuantity() <= itemRemoveDTO.quantity()){
            shoppingItemsRepository.delete(shoppingItem);
        }
        else {
            shoppingItem.setQuantity(shoppingItem.getQuantity() - itemRemoveDTO.quantity());
            shoppingItemsRepository.save(shoppingItem);
        }
    }

    /**
     * Buys the specified list of items from the shopping list.
     *
     * @param itemDTOList A list of DTO objects containing the details of the items to buy.
     * @throws StoreNotFoundException if the specified store name does not exist.
     * @throws ItemNotFoundException if the specified item name does not exist in the specified store.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws FridgeItemsNotFoundException if the specified item does not exist in the specified fridge.
     * @throws ShoppingItemsNotFoundException if the specified item does not exist in the shopping list for the specified fridge.
     */
    @Override
    public void buyItemsFromShoppingList(List<ItemRemoveDTO> itemDTOList) {
        for(ItemRemoveDTO i: itemDTOList){
            if (i.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");
            Store store = storeRepository.findByStoreName(i.store()).orElseThrow(() -> new StoreNotFoundException(i.store()));
            Long itemId = itemRepository.findByProductNameAndStore(i.itemName(), store).orElseThrow(() -> new ItemNotFoundException(i.itemName())).getItemId();
            addToFridge(itemId, i.fridgeId(), i.quantity());
            deleteItemFromShoppingList(i, false);
        }
    }

    /**
     Accepts the suggested item by finding the store, item, fridge, and shopping item associated with the given itemDTO,
     and then sets the suggestion status of the shopping item to false.

     @param itemDTO an object containing the name of the store, item, and fridge, as well as the ID of the fridge and a boolean indicating whether the item was suggested for removal
     @throws StoreNotFoundException if the store with the given name cannot be found in the store repository
     @throws ItemNotFoundException if the item with the given name cannot be found in the item repository for the given store
     @throws FridgeNotFoundException if the fridge with the given ID cannot be found in the fridge repository
     @throws ShoppingItemsNotFoundException if the shopping item associated with the given item, fridge, and suggestion status cannot be found in the shopping items repository
     */
    @Override
    public void acceptSuggestion(ItemRemoveDTO itemDTO) {
        if (itemDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");
        Store store = storeRepository.findByStoreName(itemDTO.store()).orElseThrow(() -> new StoreNotFoundException(itemDTO.store()));
        Item item = itemRepository.findByProductNameAndStore(itemDTO.itemName(), store).orElseThrow(() -> new ItemNotFoundException(itemDTO.itemName()));
        Fridge fridge = fridgeRepository.findByFridgeId(itemDTO.fridgeId()).orElseThrow(() -> new FridgeNotFoundException(itemDTO.fridgeId()));
        ShoppingItems shoppingItem = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, true).orElseThrow(() -> new ShoppingItemsNotFoundException(""));
        shoppingItem.setSuggestion(false);
        shoppingItemsRepository.save(shoppingItem);
    }



}
