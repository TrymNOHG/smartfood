package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFound;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.ItemNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.Store;
import edu.ntnu.idatt2106_2023_06.backend.repo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        StoreRepository storeRepository;

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
            itemService.addToFridge(1L, 1L, 1);

            assertDoesNotThrow(() -> {
                fridgeItemsRepository.findByItemAndFridge(item, fridge).orElseThrow();
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
            assertThrows(FridgeNotFound.class, () -> {
                itemService.addToFridge(1L, 1L, 1);

            });
        }

    }

    @Nested
    @SpringBootTest
    class DeleteItemFromFridge{


    }

    @Nested
    @SpringBootTest
    class AddToShoppingList{


    }

    @Nested
    @SpringBootTest
    class GetShoppingListItems{


    }

    @Nested
    @SpringBootTest
    class DeleteItemFromShoppingList{


    }

    @Nested
    @SpringBootTest
    class BuyItemsFromShoppingList{


    }

    @Nested
    @SpringBootTest
    class AcceptSuggestion{


    }



}
