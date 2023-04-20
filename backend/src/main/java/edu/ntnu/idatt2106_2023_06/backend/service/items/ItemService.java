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

@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {

    private final ItemRepository itemRepository;
    private final FridgeItemsRepository fridgeItemsRepository;
    private final FridgeRepository fridgeRepository;
    private final StoreRepository storeRepository;
    private final ShoppingItemsRepository shoppingItemsRepository;

    @Override
    public Long addItem(ItemDTO itemDTO) {
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

    @Override
    public void addToFridge(Long itemId, Long fridgeId, int quantity) {
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

    @Override
    public void addToShoppingList(Long itemName, Long fridgeId, int quantity, boolean suggestion) {
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

    @Override
    public List<ItemDTO> getShoppingListItems(Long fridgeId) {
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow(() -> new ItemNotFoundException(fridgeId));
        List<ShoppingItems> shoppingItems = shoppingItemsRepository.findByFridge(fridge).orElseThrow(() -> new ShoppingItemsNotFoundException(fridgeId));
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (ShoppingItems item : shoppingItems){
            itemDTOList.add(ItemMapper.toItemDTO(item.getItem(), item.getQuantity()));
        }
        return itemDTOList;
    }

    @Override
    public void deleteItemFromShoppingList(ItemRemoveDTO itemRemoveDTO, boolean suggestion) {
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

    @Override
    public void buyItemsFromShoppingList(List<ItemRemoveDTO> itemDTOList) {
        for(ItemRemoveDTO i: itemDTOList){
            Store store = storeRepository.findByStoreName(i.store()).orElseThrow();
            Long itemId = itemRepository.findByProductNameAndStore(i.itemName(), store).orElseThrow(() -> new ItemNotFoundException(i.itemName())).getItemId();
            addToFridge(itemId, i.fridgeId(), i.quantity());
            deleteItemFromShoppingList(i, false);
        }
    }

    @Override
    public void acceptSuggestion(ItemRemoveDTO itemDTO) {
        Store store = storeRepository.findByStoreName(itemDTO.store()).orElseThrow(() -> new StoreNotFoundException(itemDTO.store()));
        Item item = itemRepository.findByProductNameAndStore(itemDTO.itemName(), store).orElseThrow(() -> new ItemNotFoundException(itemDTO.itemName()));
        Fridge fridge = fridgeRepository.findByFridgeId(itemDTO.fridgeId()).orElseThrow(() -> new FridgeNotFoundException(itemDTO.fridgeId()));
        ShoppingItems shoppingItem = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, true).orElseThrow(() -> new ShoppingItemsNotFoundException(""));
        shoppingItem.setSuggestion(false);
        shoppingItemsRepository.save(shoppingItem);
    }

    @Override
    public void deleteItemFromFridge(ItemRemoveDTO itemRemoveDTO) {
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

}
