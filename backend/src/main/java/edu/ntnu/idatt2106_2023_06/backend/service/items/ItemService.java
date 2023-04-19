package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.dto.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.mapper.ItemMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.*;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.StoreRepository;
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

    @Override
    public Long addItem(ItemDTO itemDTO) {
        Item item = itemRepository.findItemByProductName(itemDTO.name()).orElse(null);
        if (item != null) return item.getItemId();
        Store store = storeRepository.findByStoreName(itemDTO.store()).orElse(null);
        if (store == null){
            Store store1 = Store.builder()
                    .storeName(itemDTO.store())
                    .build();
            storeRepository.save(store1);
        }

        store = storeRepository.findByStoreName(itemDTO.store()).orElseThrow();
        Item i = ItemMapper.toItem(itemDTO, store);
        itemRepository.save(i);

        item = itemRepository.findItemByProductName(itemDTO.name()).orElseThrow();
        return item.getItemId();
    }

    @Override
    public void addToFridge(String itemName, Long fridgeId) {
        Item item = itemRepository.findItemByProductName(itemName).orElseThrow();
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow();
        FridgeItems fridgeItem = fridgeItemsRepository.findByItemAndFridge(item, fridge).orElse(null);
        if(fridgeItem == null){
             fridgeItem = FridgeItems.builder()
                     .id(new FridgeItemsId(item.getItemId(), fridge.getFridgeId()))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .build();
        }
        else {
            fridgeItem.setQuantity(fridgeItem.getQuantity() + 1);
        }
        fridgeItemsRepository.save(fridgeItem);
    }

    @Override
    public List<ItemDTO> getFridgeItems(Long fridgeId) {
        Fridge fridge = fridgeRepository.findByFridgeId(fridgeId).orElseThrow();
        List<FridgeItems> fridgeItems = fridgeItemsRepository.findByFridge(fridge).orElseThrow();
        List<Item> itemList = new ArrayList<>();
        for (FridgeItems item : fridgeItems){
            itemList.add(item.getItem());
        }
        return itemList.stream().map(ItemMapper::toItemDTO).toList();
    }
}
