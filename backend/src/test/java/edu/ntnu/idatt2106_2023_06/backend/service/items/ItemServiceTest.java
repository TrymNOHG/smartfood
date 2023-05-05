package edu.ntnu.idatt2106_2023_06.backend.service.items;

import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemMoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemSearchDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.RecipeShoppingDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingItemUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingListLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.*;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.FridgeNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.ItemNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.ItemMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.*;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeMemberRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ShoppingItemsRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.store.StoreRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import edu.ntnu.idatt2106_2023_06.backend.sortAndFilter.FilterRequest;
import edu.ntnu.idatt2106_2023_06.backend.sortAndFilter.SearchRequest;
import edu.ntnu.idatt2106_2023_06.backend.sortAndFilter.SortRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
                    "Kiwi", 200000,
                    null, 1, null, "12345678");
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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);

            ItemDTO itemDTO = ItemMapper.toItemDTO(item, 1, true);

            itemService.addToFridge(itemDTO, 1L);

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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ItemDTO itemDTO = ItemMapper.toItemDTO(item, 1, true);

            itemService.addToFridge(itemDTO, 1L);
            itemService.addToFridge(itemDTO, 1L);
            FridgeItems fridgeItems = fridgeItemsRepository.findByItemAndFridge(item, fridge).orElseThrow();

            assertEquals(200, fridgeItems.getAmount());
        }

//        @Test
//        @Transactional
//        void throws_ItemNotFoundException(){
//            Fridge fridge = Fridge.builder()
//                    .fridgeId(1L)
//                    .fridgeName("testFridge")
//                    .build();
//            fridgeRepository.save(fridge);
//
//            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
//                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
//                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//            ItemDTO itemDTO = ItemMapper.toItemDTO(item, 1, true);
//
//            itemService.addToFridge(itemDTO, 1L);
//            assertThrows(ItemNotFoundException.class, () -> {
//                itemService.addToFridge(itemDTO, 1L);
//
//            });
//        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            assertThrows(FridgeNotFoundException.class, () -> {
                ItemDTO itemDTO = ItemMapper.toItemDTO(item, 1, true);
                itemService.addToFridge(itemDTO, 1L);
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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItems fridgeItems = FridgeItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .amount(1)
                    .expirationDate(LocalDateTime.now())
                    .purchaseDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);
            List<FridgeItemLoadDTO> itemDTOList = itemService.getFridgeItems(1L);

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

        @Autowired
        UserRepository userRepository;

        @Autowired
        JwtService jwtService;

        @Autowired
        protected UserDetailsService userDetailsService;

        @BeforeEach
        public void setUp() {
            User user = User.builder()
                    .userId(1L).email("test@test.test")
                    .firstName("test")
                    .lastName("test")
                    .password("test")
                    .username("testUser")
                                    .build();
            userRepository.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");
            String jwt = jwtService.generateToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
        }

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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItems fridgeItems = FridgeItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .amount(1)
                    .expirationDate(LocalDateTime.now())
                    .purchaseDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 0);
            itemService.removeItemFromFridge(itemRemoveDTO);

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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 1000.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItems fridgeItems = FridgeItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .amount(3000)
                    .expirationDate(LocalDateTime.now())
                    .purchaseDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 500);
            itemService.removeItemFromFridge(itemRemoveDTO);
            FridgeItems fridgeItems1 = fridgeItemsRepository.findByItemAndFridge(item,fridge).orElseThrow();
            assertEquals(500.0, fridgeItems1.getAmount());
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
                itemService.removeItemFromFridge(itemRemoveDTO);

            });
        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){

            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(FridgeNotFoundException.class, () -> {
                itemService.removeItemFromFridge(itemRemoveDTO);

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
                itemService.removeItemFromFridge(itemRemoveDTO);

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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(FridgeItemsNotFoundException.class, () -> {
                itemService.removeItemFromFridge(itemRemoveDTO);

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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);

            ItemDTO itemDTO = ItemMapper.toItemDTO(item, 1, true);

            itemService.addToShoppingList(itemDTO, 1L,  false);

            assertDoesNotThrow(() -> {
                shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, false).orElseThrow();
                shoppingItemsRepository.findByItem_ItemIdAndFridge_FridgeId(item.getItemId(), fridge.getFridgeId()).orElseThrow();

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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);

            ItemDTO itemDTO = ItemMapper.toItemDTO(item, 1, true);


            itemService.addToShoppingList(itemDTO, 1L,  false);
            itemService.addToShoppingList(itemDTO, 1L,  false);
            ShoppingItems shoppingItems = shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item, fridge, false).orElseThrow();

            assertEquals(2, shoppingItems.getQuantity());
        }

        @Test
        @Transactional
        void throws_IllegalArgumentException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);

            ItemDTO itemDTO = ItemDTO
                    .builder()
                    .name("Melk")
                    .store("Dairy")
                    .EAN("12356")
                    .build();


            assertThrows(IllegalArgumentException.class, () -> {
                itemService.addToShoppingList(itemDTO, 1L,  false);

            });
        }

        @Test
        @Transactional
        void throws_IllegalArgumentException_when_list_is_empty(){
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);

            ItemDTO itemDTO = ItemDTO
                    .builder()
                    .name("Melk")
                    .store("Dairy")
                    .EAN("12356")
                    .build();

            assertThrows(IllegalArgumentException.class, () -> {
                itemService.addToShoppingList(itemDTO, 1L,  false);

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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ShoppingItems shoppingItems = ShoppingItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .suggestion(false)
                    .build();
            shoppingItemsRepository.save(shoppingItems);
            List<ShoppingListLoadDTO> itemDTOList = itemService.getShoppingListItems(1L);

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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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
            itemService.removeItemFromShoppingList(itemRemoveDTO ,false);

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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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
            itemService.removeItemFromShoppingList(itemRemoveDTO, false);
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
                itemService.removeItemFromShoppingList(itemRemoveDTO, false);

            });
        }

        @Test
        @Transactional
        void throws_FridgeNotFoundException(){

            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(FridgeNotFoundException.class, () -> {
                itemService.removeItemFromShoppingList(itemRemoveDTO, false);

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
                itemService.removeItemFromShoppingList(itemRemoveDTO, false);

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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
            assertThrows(ShoppingItemsNotFoundException.class, () -> {
                itemService.removeItemFromShoppingList(itemRemoveDTO, false);

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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ShoppingItems shoppingItems = ShoppingItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .suggestion(false)
                    .build();
            shoppingItemsRepository.save(shoppingItems);
            List<ItemMoveDTO> itemRemoveDTOList = new ArrayList<>();
            ItemMoveDTO itemMoveDTO = new ItemMoveDTO(1L, 1L);
            itemRemoveDTOList.add(itemMoveDTO);
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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            ShoppingItems shoppingItems = ShoppingItems.builder()
                    .id(new FridgeItemsId(1L, 1L))
                    .item(item)
                    .fridge(fridge)
                    .quantity(1)
                    .suggestion(false)
                    .build();
            shoppingItemsRepository.save(shoppingItems);
            List<ItemMoveDTO> itemRemoveDTOList = new ArrayList<>();
            ItemMoveDTO itemMoveDTO = new ItemMoveDTO(1L, 1L);
            itemRemoveDTOList.add(itemMoveDTO);
            itemService.buyItemsFromShoppingList(itemRemoveDTOList);

            assertTrue(shoppingItemsRepository.findByItemAndFridgeAndSuggestion(item,fridge,false).isEmpty());
        }

        @Test
        @Transactional
        void throws_ShoppingItemsNotFoundException_when_item_is_not_saved(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Store store = Store.builder().storeId(1L).storeName("Dairy").build();
            storeRepository.save(store);
            List<ItemMoveDTO> itemMoveDTOS = new ArrayList<>();
            ItemMoveDTO itemMoveDTO = new ItemMoveDTO(1L, 1L);
            itemMoveDTOS.add(itemMoveDTO);
            assertThrows(ShoppingItemsNotFoundException.class, () -> {
                itemService.buyItemsFromShoppingList(itemMoveDTOS);

            });
        }

        @Test
        @Transactional
        void throws_ShoppingItemsNotFoundException_when_store_is_not_present(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);

            List<ItemMoveDTO> itemRemoveDTOList = new ArrayList<>();
            ItemMoveDTO itemMoveDTO = new ItemMoveDTO(1L, 1L);
            itemRemoveDTOList.add(itemMoveDTO);
            assertThrows(ShoppingItemsNotFoundException.class, () -> {
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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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


    @Nested
    @SpringBootTest
    class AddIngredientsToShoppingList {

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
        UserRepository userRepository;

        @Autowired
        FridgeMemberRepository fridgeMemberRepository;

        @Autowired
        ItemService itemService;

        @Test
        @Transactional
        void throws_UnauthorizedException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            FridgeMemberId fridgeMemberId = new FridgeMemberId(1L, 1L);
            FridgeMember fridgeMember = new FridgeMember(fridgeMemberId, user, fridge, true);
            fridgeMemberRepository.save(fridgeMember);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);


            RecipeShoppingDTO recipeShoppingDTO = new RecipeShoppingDTO(1L, new ArrayList<>());

            assertThrows(UnauthorizedException.class, () -> itemService.addIngredientsToShoppingList(recipeShoppingDTO, "hans"));
        }


        @Test
        @Transactional
        void does_not_throw_exception(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            FridgeMemberId fridgeMemberId = new FridgeMemberId(1L, 1L);
            FridgeMember fridgeMember = new FridgeMember(fridgeMemberId, user, fridge, true);
            fridgeMemberRepository.save(fridgeMember);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);


            RecipeShoppingDTO recipeShoppingDTO = new RecipeShoppingDTO(1L, new ArrayList<>());

            assertDoesNotThrow(() -> itemService.addIngredientsToShoppingList(recipeShoppingDTO, "Ole123"));
        }

    }

    @Nested
    @SpringBootTest
    class UpdateFridgeItem {

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
        UserRepository userRepository;

        @Autowired
        FridgeMemberRepository fridgeMemberRepository;

        @Autowired
        ItemService itemService;

        @Test
        @Transactional
        void updates_correct_amount(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItem = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItem);


            FridgeItemUpdateDTO fridgeItemUpdateDTO = new FridgeItemUpdateDTO(1L, 1L, 2.0, LocalDateTime.now(), LocalDateTime.now());
            itemService.updateFridgeItem(fridgeItemUpdateDTO);
            FridgeItems fridgeItem2 = fridgeItemsRepository.findByItem_ItemIdAndFridge_FridgeId(1L, 1L).orElseThrow();
            assertEquals(2, fridgeItem2.getAmount());
        }

        @Test
        @Transactional
        void throws_FridgeItemsNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);



            FridgeItemUpdateDTO fridgeItemUpdateDTO = new FridgeItemUpdateDTO(1L, 1L, 2.0, LocalDateTime.now(), LocalDateTime.now());
            assertThrows(FridgeItemsNotFoundException.class, () -> itemService.updateFridgeItem(fridgeItemUpdateDTO));

        }
    }

    @Nested
    @SpringBootTest
    class UpdateShoppingItem {

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
        UserRepository userRepository;

        @Autowired
        FridgeMemberRepository fridgeMemberRepository;

        @Autowired
        ItemService itemService;

        @Test
        @Transactional
        void updates_correct_amount(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            ShoppingItems shoppingItem = ShoppingItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .quantity(1)
                    .suggestion(false)
                    .build();
            shoppingItemsRepository.save(shoppingItem);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);

            ShoppingItemUpdateDTO shoppingItemUpdateDTO = new ShoppingItemUpdateDTO(1L, 1L, 2, false);
            itemService.updateShoppingItem(shoppingItemUpdateDTO, "Ole123");
            ShoppingItems shoppingItem2 = shoppingItemsRepository.findByItem_ItemIdAndFridge_FridgeId(1L, 1L).orElseThrow();
            assertEquals(2, shoppingItem2.getQuantity());
        }

        @Test
        @Transactional
        void throws_ShoppingItemsNotFoundException(){
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);


            ShoppingItemUpdateDTO shoppingItemUpdateDTO = new ShoppingItemUpdateDTO(1L, 1L, 2, false);
            assertThrows(ShoppingItemsNotFoundException.class, () -> itemService.updateShoppingItem(shoppingItemUpdateDTO, "Ole123"));

        }

        @Test
        @Transactional
        void throws_UsernameNotFoundException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            ShoppingItems shoppingItem = ShoppingItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .quantity(1)
                    .suggestion(false)
                    .build();
            shoppingItemsRepository.save(shoppingItem);


            ShoppingItemUpdateDTO shoppingItemUpdateDTO = new ShoppingItemUpdateDTO(1L, 1L, 2, false);
            assertThrows(UsernameNotFoundException.class, () -> itemService.updateShoppingItem(shoppingItemUpdateDTO, "test"));

        }
    }

    @Nested
    @SpringBootTest
    class SearchFridgeItems {

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
        UserRepository userRepository;

        @Autowired
        FridgeMemberRepository fridgeMemberRepository;

        @Autowired
        ItemService itemService;

        @Autowired
        JwtService jwtService;

        @Autowired
        UserDetailsService userDetailsService;


        @Test
        @Transactional
        void does_not_throw_exception(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername("Ole123");
            String jwt = jwtService.generateToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
            FridgeMemberId fridgeMemberId = new FridgeMemberId(1L, 1L);
            FridgeMember fridgeMember = new FridgeMember(fridgeMemberId, user, fridge, true);
            fridgeMemberRepository.save(fridgeMember);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);


            FridgeItemSearchDTO fridgeItemSearchDTO = new FridgeItemSearchDTO(1L, "Tine Melk", "expirationDate", "ASC", 0, 1);
            assertDoesNotThrow( () -> itemService.searchFridgeItems(fridgeItemSearchDTO));

        }



        @Test
        @Transactional
        void throws_UnauthorizedException(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .build();
            fridgeRepository.save(fridge);
            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername("Ole123");
            String jwt = jwtService.generateToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);
            FridgeItemsId fridgeItemsId = new FridgeItemsId(1L, 1L);
            FridgeItems fridgeItems = FridgeItems
                    .builder()
                    .id(fridgeItemsId)
                    .fridge(fridge)
                    .item(item)
                    .purchaseDate(LocalDateTime.now())
                    .expirationDate(LocalDateTime.now())
                    .build();
            fridgeItemsRepository.save(fridgeItems);


            FridgeItemSearchDTO fridgeItemSearchDTO = new FridgeItemSearchDTO(1L, "Tine Melk", "expirationDate", "ASC", 0, 1);
            assertThrows(UnauthorizedException.class, () -> itemService.searchFridgeItems(fridgeItemSearchDTO));

        }


    }

    @Nested
    @SpringBootTest
    class DeleteAllItemsFromShoppingList {

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
        UserRepository userRepository;

        @Autowired
        FridgeMemberRepository fridgeMemberRepository;

        @Autowired
        ItemService itemService;

        @Autowired
        JwtService jwtService;

        @Autowired
        UserDetailsService userDetailsService;

        @Test
        @Transactional
        void does_not_throw_exception(){
            Fridge fridge = Fridge.builder()
                    .fridgeId(1L)
                    .fridgeName("testFridge")
                    .stats(new HashSet<>())
                    .build();
            fridgeRepository.save(fridge);
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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
            List<ItemRemoveDTO> itemRemoveDTOList = List.of(itemRemoveDTO);
            assertDoesNotThrow(() -> itemService.deleteAllItemsFromShoppingList(itemRemoveDTOList));
        }
    }

    @Nested
    @SpringBootTest
    class AddUnitToExistingItems {

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
        UserRepository userRepository;

        @Autowired
        FridgeMemberRepository fridgeMemberRepository;

        @Autowired
        ItemService itemService;

        @Autowired
        JwtService jwtService;

        @Autowired
        UserDetailsService userDetailsService;

        @Test
        @Transactional
        void does_not_throw_exception(){
            Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            itemRepository.save(item);

            assertDoesNotThrow(() -> itemService.addUnitToExistingItems());
        }
    }


}
