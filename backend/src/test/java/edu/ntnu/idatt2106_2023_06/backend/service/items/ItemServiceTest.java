package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.*;
import edu.ntnu.idatt2106_2023_06.backend.model.*;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.ItemNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.Store;
import edu.ntnu.idatt2106_2023_06.backend.repo.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ItemServiceTest {



    @Nested
    @SpringBootTest
    class AddItem{

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        ItemService itemService;


        @Test
        @Transactional
        void adds_correct_Item(){

            ItemDTO itemDTO = new ItemDTO( "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    "Kiwi", 200000, new Date(), new Date(),
                    null, 1);
            itemService.addItem(itemDTO);
            assertDoesNotThrow(() -> {
                itemRepository.findByItemId(1L).orElseThrow();
            });
        }
    }

    @Nested
    @SpringBootTest
    class AddToFridge{

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        ItemService itemService;

        @Test
        @Transactional
        void adds_correct_Item(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            itemService.addToFridge(1L, 1L, 1);

            assertDoesNotThrow(() -> {
                fridgeItemsRepository.findByItemAndFridge(item, fridge).orElseThrow();
            });
        }

        @Test
        @Transactional
        void adds_correct_quantity(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            itemService.addToFridge(1L, 1L, 1);
            itemService.addToFridge(1L, 1L, 1);
            FridgeItems fridgeItems = fridgeItemsRepository.findByItemAndFridge(item, fridge).orElseThrow();

            assertEquals(2, fridgeItems.getQuantity());
        }

        @Test
        @Transactional
        void throws_ItemNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            assertThrows(ItemNotFoundException.class, () -> {
                itemService.addToFridge(1L, 1L, 1);

            });
        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            assertThrows(FridgeNotFoundException.class, () -> {
                itemService.addToFridge(1L, 1L, 1);

            });
        }

    }

    @Nested
    @SpringBootTest
    class getFridgeItems{

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        StoreRepository storeRepository;

        @Autowired
        ItemService itemService;

        @Test
        @Transactional
        void gets_correct_Item(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItems fridgeItems = FridgeItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .build();
            fridgeItemsRepository.save(fridgeItems);
            List<ItemDTO> itemDTOList = itemService.getFridgeItems(1L);

            assertEquals("Tine Melk", itemDTOList.get(0).name());
        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){

            assertThrows(FridgeNotFoundException.class, () -> {
                itemService.getFridgeItems(1L);

            });
        }


    }

    @Nested
    @SpringBootTest
    class DeleteItemFromFridge{

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        StoreRepository storeRepository;

        @Autowired
        ItemService itemService;

        @Test
        @Transactional
        void removes_correct_Item(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .stats(new HashSet<>())
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItems fridgeItems = FridgeItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .build();
            fridgeItemsRepository.save(fridgeItems);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1)
;            itemService.deleteItemFromFridge(itemRemoveDTO);

            assertTrue(fridgeItemsRepository.findByItemAndFridge(item,fridge).isEmpty());
        }

        @Test
        @Transactional
        void removes_correct_quantity(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItems fridgeItems = FridgeItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(3)
                    .build();
            fridgeItemsRepository.save(fridgeItems);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 2);
            itemService.deleteItemFromFridge(itemRemoveDTO);
            FridgeItems fridgeItems1 = fridgeItemsRepository.findByItemAndFridge(item,fridge).orElseThrow();
            assertEquals(1, fridgeItems1.getQuantity());
        }

        @Test
        @Transactional
        void throws_StoreNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);

            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(StoreNotFoundException.class, () -> {
                itemService.deleteItemFromFridge(itemRemoveDTO);

            });
        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){

            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(FridgeNotFoundException.class, () -> {
                itemService.deleteItemFromFridge(itemRemoveDTO);

            });
        }

        @Test
        @Transactional
        void throws_ItemNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(ItemNotFoundException.class, () -> {
                itemService.deleteItemFromFridge(itemRemoveDTO);

            });
        }

        @Test
        @Transactional
        void throws_FridgeItemsNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(FridgeItemsNotFoundException.class, () -> {
                itemService.deleteItemFromFridge(itemRemoveDTO);

            });
        }


    }

    @Nested
    @SpringBootTest
    class AddToShoppingList{


        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        ShoppingItemsRepository shoppingItemsRepository;

        @Autowired
        ItemService itemService;

        @Test
        @Transactional
        void adds_correct_Item(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            itemService.addToShoppingList(1L, 1L, 1, false);

            assertDoesNotThrow(() -> {
                shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, false).orElseThrow();
            });
        }

        @Test
        @Transactional
        void adds_correct_quantity(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            itemService.addToShoppingList(1L, 1L, 1, false);
            itemService.addToShoppingList(1L, 1L, 1, false);
            ShoppingItems shoppingItems = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, false).orElseThrow();

            assertEquals(2, shoppingItems.getQuantity());
        }

        @Test
        @Transactional
        void throws_ItemNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            assertThrows(ItemNotFoundException.class, () -> {
                itemService.addToShoppingList(1L, 1L, 1, false);

            });
        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            assertThrows(FridgeNotFoundException.class, () -> {
                itemService.addToShoppingList(1L, 1L, 1, false);

            });
        }

    }

    @Nested
    @SpringBootTest
    class GetShoppingListItems{
        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        ShoppingItemsRepository shoppingItemsRepository;

        @Autowired
        StoreRepository storeRepository;

        @Autowired
        ItemService itemService;

        @Test
        @Transactional
        void gets_correct_Item(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ShoppingItems shoppingItems = ShoppingItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .suggestion(false)
                    .build();
            shoppingItemsRepository.save(shoppingItems);
            List<ItemDTO> itemDTOList = itemService.getShoppingListItems(1L);

            assertEquals("Tine Melk", itemDTOList.get(0).name());
        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){

            assertThrows(FridgeNotFoundException.class, () -> {
                itemService.getShoppingListItems(1L);

            });
        }

    }

    @Nested
    @SpringBootTest
    class DeleteItemFromShoppingList{
        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        ShoppingItemsRepository shoppingItemsRepository;

        @Autowired
        StoreRepository storeRepository;

        @Autowired
        ItemService itemService;

        @Test
        @Transactional
        void removes_correct_Item(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ShoppingItems shoppingItems = ShoppingItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .suggestion(false)
                    .build();
            shoppingItemsRepository.save(shoppingItems);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            itemService.deleteItemFromShoppingList(itemRemoveDTO ,false);

            assertTrue(shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item,fridge, false).isEmpty());
        }

        @Test
        @Transactional
        void removes_correct_quantity(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ShoppingItems shoppingItems = ShoppingItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(3)
                    .suggestion(false)
                    .build();
            shoppingItemsRepository.save(shoppingItems);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 2);
            itemService.deleteItemFromShoppingList(itemRemoveDTO, false);
            ShoppingItems shoppingItems1 = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item,fridge, false).orElseThrow();
            assertEquals(1, shoppingItems1.getQuantity());
        }

        @Test
        @Transactional
        void throws_StoreNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);

            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(StoreNotFoundException.class, () -> {
                itemService.deleteItemFromShoppingList(itemRemoveDTO, false);

            });
        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){

            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(FridgeNotFoundException.class, () -> {
                itemService.deleteItemFromShoppingList(itemRemoveDTO, false);

            });
        }

        @Test
        @Transactional
        void throws_ItemNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(ItemNotFoundException.class, () -> {
                itemService.deleteItemFromShoppingList(itemRemoveDTO, false);

            });
        }

        @Test
        @Transactional
        void throws_ShoppingItemsNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(ShoppingItemsNotFoundException.class, () -> {
                itemService.deleteItemFromShoppingList(itemRemoveDTO, false);

            });
        }

    }

    @Nested
    @SpringBootTest
    class BuyItemsFromShoppingList{

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        ShoppingItemsRepository shoppingItemsRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        StoreRepository storeRepository;

        @Autowired
        ItemService itemService;

        @Test
        @Transactional
        void adds_correct_items_to_fridge(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ShoppingItems shoppingItems = ShoppingItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .suggestion(false)
                    .build();
            shoppingItemsRepository.save(shoppingItems);
            List<ItemRemoveDTO> itemRemoveDTOList = new ArrayList<>();
            itemRemoveDTOList.add(new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1));
            itemService.buyItemsFromShoppingList(itemRemoveDTOList);

            assertDoesNotThrow(() -> {
                fridgeItemsRepository.findByItemAndFridge(item,fridge);
            });
        }

        @Test
        @Transactional
        void removes_correct_items_from_shopping_list(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ShoppingItems shoppingItems = ShoppingItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .suggestion(false)
                    .build();
            shoppingItemsRepository.save(shoppingItems);
            List<ItemRemoveDTO> itemRemoveDTOList = new ArrayList<>();
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            itemRemoveDTOList.add(itemRemoveDTO);
            itemService.buyItemsFromShoppingList(itemRemoveDTOList);

            assertTrue(shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item,fridge,false).isEmpty());
        }

        @Test
        @Transactional
        void throws_ItemNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            List<ItemRemoveDTO> itemRemoveDTOList = new ArrayList<>();
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            itemRemoveDTOList.add(itemRemoveDTO);
            assertThrows(ItemNotFoundException.class, () -> {
                itemService.buyItemsFromShoppingList(itemRemoveDTOList);

            });
        }

        @Test
        @Transactional
        void throws_StoreNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);

            List<ItemRemoveDTO> itemRemoveDTOList = new ArrayList<>();
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            itemRemoveDTOList.add(itemRemoveDTO);
            assertThrows(StoreNotFoundException.class, () -> {
                itemService.buyItemsFromShoppingList(itemRemoveDTOList);

            });
        }
    }

    @Nested
    @SpringBootTest
    class AcceptSuggestion{

        @Autowired
        FridgeRepository fridgeRepository;

        @Autowired
        ItemRepository itemRepository;

        @Autowired
        ShoppingItemsRepository shoppingItemsRepository;

        @Autowired
        FridgeItemsRepository fridgeItemsRepository;

        @Autowired
        StoreRepository storeRepository;

        @Autowired
        ItemService itemService;

        @Test
        @Transactional
        void adds_correct_items_to_shopping_list(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ShoppingItems shoppingItems = ShoppingItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .suggestion(true)
                    .build();
            shoppingItemsRepository.save(shoppingItems);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            itemService.acceptSuggestion(itemRemoveDTO);

            assertDoesNotThrow(() -> {
                shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item,fridge, false);
            });
        }

        @Test
        @Transactional
        void removes_correct_items_from_suggestions(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ShoppingItems shoppingItems = ShoppingItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .suggestion(true)
                    .build();
            shoppingItemsRepository.save(shoppingItems);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            itemService.acceptSuggestion(itemRemoveDTO);

            assertTrue(shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item,fridge, true).isEmpty());
        }


        @Test
        @Transactional
        void throws_ItemNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(ItemNotFoundException.class, () -> {
                itemService.acceptSuggestion(itemRemoveDTO);
            });
        }

        @Test
        @Transactional
        void throws_StoreNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);

            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(StoreNotFoundException.class, () -> {
                itemService.acceptSuggestion(itemRemoveDTO);

            });
        }
    }



}
