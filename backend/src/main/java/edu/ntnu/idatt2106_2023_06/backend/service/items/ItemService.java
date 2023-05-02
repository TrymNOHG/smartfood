package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.*;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingItemUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingListLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.*;
import edu.ntnu.idatt2106_2023_06.backend.mapper.FridgeItemMapper;
import edu.ntnu.idatt2106_2023_06.backend.mapper.ItemMapper;
import edu.ntnu.idatt2106_2023_06.backend.mapper.ShoppingItemMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItemsId;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ShoppingItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.store.StoreRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private final UserRepository userRepository;

    //TODO: add
    //        if (itemDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");
    /**
     * Adds an item to the item database.
     *
     * @param itemDTO                   The ItemDTO containing the item to add.
     * @return                          The item created or found using the itemDTO.
     * @throws StoreNotFoundException   if the store cannot be found.
     * @throws ItemNotFoundException    if the item cannot be found.
     */
    @Override
    public Item addItem(ItemDTO itemDTO) {
        if (itemDTO.price() < 0) throw  new IllegalArgumentException("Cannot have negative price");
        Store store = storeRepository.findByStoreName(itemDTO.store())
                .orElseGet(() -> storeRepository.save(
                Store.builder()
                .storeName(itemDTO.store())
                .itemsInStore(new ArrayList<>())
                .build()));

        Item item = itemRepository.findByProductNameAndStore(itemDTO.name(), store).orElse(null);
        if (item != null) {
            item.setPrice(itemDTO.price());
            itemRepository.save(item);
            return item;
        }

        System.out.println(itemDTO);
        Item i = ItemMapper.toItem(itemDTO, store);
        itemRepository.save(i);

        item = itemRepository.findByProductNameAndStore(itemDTO.name(), store).orElseThrow(() -> new ItemNotFoundException(itemDTO.name()));
        return item;
    }

    /**
     * Adds an item to the fridge.
     *
     * @param itemDTO The dto containing the item information
     * @param fridgeId The ID of the fridge to add the item to.
     * @throws ItemNotFoundException if the item cannot be found.
     * @throws FridgeNotFoundException if the fridge cannot be found.
     */
    @Override
    public void addToFridge(ItemDTO itemDTO, Long fridgeId) {
        logger.info("User wants to add a new item to fridge");
        Item item = addItem(itemDTO);

        if (itemDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");

        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        FridgeItems fridgeItem = fridgeItemsRepository.findByItemAndFridge(item, fridge).orElseGet(() ->
                FridgeItems.builder()
                .id(new FridgeItemsId(item.getItemId(), fridge.getFridgeId()))
                .item(item)
                .fridge(fridge)
                .quantity(0)
                .purchaseDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(4)) //TODO: change to a valid expiration date....
                .build());

        fridgeItem.setQuantity(fridgeItem.getQuantity() + itemDTO.quantity());

        fridgeItemsRepository.save(fridgeItem);
    }

    /**
     * Adds an item to the shopping list for the specified fridge.
     *
     * @param itemDTO The itemDTO containing the essential information for an item.
     * @param fridgeId The ID of the fridge to add the item to the shopping list for.
     * @param suggestion A boolean indicating whether the item is a suggested item or not.
     * @throws ItemNotFoundException if the specified item ID does not exist.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     */
    @Override
    public void addToShoppingList(ItemDTO itemDTO, Long fridgeId, boolean suggestion) {
        logger.info("User wants to add a new item to shopping list");
        Item item = addItem(itemDTO);

        if (itemDTO.quantity() <= 0) throw  new IllegalArgumentException("Cannot have zero or negative quantity");


        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        ShoppingItems shoppingItem = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, suggestion).orElse(null);
        if(shoppingItem == null){
            shoppingItem = ShoppingItems.builder()
                    .id(new FridgeItemsId(item.getItemId(), fridge.getFridgeId()))
                    .item(item)
                    .fridge(fridge)
                    .quantity(itemDTO.quantity())
                    .suggestion(suggestion)
                    .build();
        }
        else {
            shoppingItem.setQuantity(shoppingItem.getQuantity() + itemDTO.quantity());
        }
        shoppingItemsRepository.save(shoppingItem);
    }

    /**
     * This method updates a fridge item
     *
     * @param fridgeItemUpdateDTO   The DTO containing the information of the update fridge item.
     */
    @Transactional
    public void updateFridgeItem(FridgeItemUpdateDTO fridgeItemUpdateDTO){
        logger.info("Searching for the fridge item to change");
        FridgeItems fridgeItem = fridgeItemsRepository.findByItem_ItemIdAndFridge_FridgeId(fridgeItemUpdateDTO.itemId(), fridgeItemUpdateDTO.fridgeId())
                .orElseThrow(() -> new FridgeItemsNotFoundException(fridgeItemUpdateDTO.itemId()));
        logger.info("Fridge item was found");

        logger.info("Changing the fridge item");
        fridgeItem.setQuantity(fridgeItemUpdateDTO.quantity() != null && fridgeItemUpdateDTO.quantity() >= 1 ?
                fridgeItemUpdateDTO.quantity() : fridgeItem.getQuantity());

        fridgeItem.setPurchaseDate(fridgeItemUpdateDTO.purchaseDate() != null ?
                fridgeItemUpdateDTO.purchaseDate() : fridgeItem.getPurchaseDate());

        fridgeItem.setExpirationDate(fridgeItemUpdateDTO.expirationDate() != null ?
                fridgeItemUpdateDTO.expirationDate() : fridgeItem.getExpirationDate());

        fridgeItemsRepository.save(fridgeItem);
    }

    /**
     * This method updates a shopping list item. This may for example be changing a suggestion to an item that can
     * be bought.
     *
     * @param shoppingItemUpdateDTO   The DTO containing the information of the update shopping list item.
     */
    @Transactional
    public void updateShoppingItem(ShoppingItemUpdateDTO shoppingItemUpdateDTO, String username){
        logger.info("Searching for the shopping item to change");
        ShoppingItems shoppingItem = shoppingItemsRepository.findByItem_ItemIdAndFridge_FridgeId(shoppingItemUpdateDTO.itemId(), shoppingItemUpdateDTO.fridgeId())
                .orElseThrow(() -> new FridgeItemsNotFoundException(shoppingItemUpdateDTO.itemId()));
        logger.info("Shopping item was found");

        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username));

        logger.info("Changing the shopping item");
        shoppingItem.setQuantity(shoppingItemUpdateDTO.quantity() != null && shoppingItemUpdateDTO.quantity() >= 1 ?
                shoppingItemUpdateDTO.quantity() : shoppingItem.getQuantity());

        shoppingItem.setSuggestion(shoppingItemUpdateDTO.suggestion() != null ?
                shoppingItemUpdateDTO.suggestion() : shoppingItem.isSuggestion());

        shoppingItem.setUser(user);

        shoppingItemsRepository.save(shoppingItem);
    }

    /**
     * Removes the specified quantity of an item from the specified fridge.
     *
     * @param itemRemoveDTO A DTO object containing the details of the item to remove.
     * @throws StoreNotFoundException if the specified store name does not exist.
     * @throws ItemNotFoundException if the specified item name does not exist in the specified store.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws FridgeItemsNotFoundException if the specified item does not exist in the specified fridge.
     */
    @Override
    public void removeItemFromFridge(ItemRemoveDTO itemRemoveDTO) {
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
     * Removes the specified quantity of an item from the shopping list for the specified fridge.
     *
     * @param itemRemoveDTO A DTO object containing the details of the item to remove.
     * @param suggestion A boolean indicating whether the item is a suggested item or not.
     * @throws StoreNotFoundException if the specified store name does not exist.
     * @throws ItemNotFoundException if the specified item name does not exist in the specified store.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws ShoppingItemsNotFoundException if the specified item does not exist in the shopping list for the specified fridge.
     */
    @Override
    public void removeItemFromShoppingList(ItemRemoveDTO itemRemoveDTO, boolean suggestion) {
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
     * Deletes the specified quantity of many items from the shopping list for the specified fridge.
     *
     * @param itemRemoveDTOList A DTO object containing the details of the items to remove as a list.
     */
    @Override
    public void deleteAllItemsFromShoppingList(List<ItemRemoveDTO> itemRemoveDTOList) {
        for(ItemRemoveDTO i: itemRemoveDTOList){
            removeItemFromShoppingList(i, false);
        }
    }

    /**
     * Buys the specified list of items from the shopping list.
     *
     * @param shoppingItemIds A list of DTO objects containing the details of the items to buy.
     * @throws StoreNotFoundException if the specified store name does not exist.
     * @throws ItemNotFoundException if the specified item name does not exist in the specified store.
     * @throws FridgeNotFoundException if the specified fridge ID does not exist.
     * @throws FridgeItemsNotFoundException if the specified item does not exist in the specified fridge.
     * @throws ShoppingItemsNotFoundException if the specified item does not exist in the shopping list for the specified fridge.
     */
    @Transactional
    @Override
    public void buyItemsFromShoppingList(List<ItemMoveDTO> shoppingItemIds) {
        for(ItemMoveDTO itemMoveDTO: shoppingItemIds){
            ShoppingItems shoppingItem = shoppingItemsRepository.findByItem_ItemIdAndFridge_FridgeId(itemMoveDTO.itemId(), itemMoveDTO.fridgeId())
                    .orElseThrow(() -> new ShoppingItemsNotFoundException(itemMoveDTO.itemId()));

            if(shoppingItem.isSuggestion()) continue;

            logger.info("Removing shopping item from list");
            shoppingItemsRepository.delete(shoppingItem);

            FridgeItems fridgeItem = fridgeItemsRepository.findByItem_ItemIdAndFridge_FridgeId(itemMoveDTO.itemId(), itemMoveDTO.fridgeId())
                    .orElseGet(() -> FridgeItemMapper.toFridgeItems(shoppingItem));
            fridgeItem.setQuantity(fridgeItem.getQuantity() + shoppingItem.getQuantity());
            fridgeItemsRepository.save(fridgeItem);
            logger.info("Item has been saved or added to the fridge's item list");
        }
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
    public List<FridgeItemLoadDTO> getFridgeItems(Long fridgeId) {
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        List<FridgeItems> fridgeItems = fridgeItemsRepository.findByFridge(fridge).orElseThrow(() -> new FridgeItemsNotFoundException(fridgeId));
        List<FridgeItemLoadDTO> itemDTOList = new ArrayList<>();
        for (FridgeItems item : fridgeItems){
            itemDTOList.add(FridgeItemMapper.toFridgeItemLoadDTO(item));
        }
        return itemDTOList;
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
    public List<ShoppingListLoadDTO> getShoppingListItems(Long fridgeId) {
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new FridgeNotFoundException(fridgeId));
        List<ShoppingItems> shoppingItems = shoppingItemsRepository.findByFridge(fridge).orElseThrow(() -> new ShoppingItemsNotFoundException(fridgeId));
        List<ShoppingListLoadDTO> itemDTOList = new ArrayList<>();
        for (ShoppingItems item : shoppingItems){
            itemDTOList.add(ShoppingItemMapper.toShoppingListLoadDTO(item.getItem(), item.getQuantity(), item.isSuggestion()));
        }
        return itemDTOList;
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
