package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class FridgeItemsTest {

    @Nested
    class FridgeItems_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                FridgeItems items = new FridgeItems();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                        new Store(1L, "Dairy", new ArrayList<>()), 200000,
                        null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                FridgeItems items = new FridgeItems(null, item, new Fridge(), 1, LocalDateTime.now(), LocalDateTime.now());
            } catch (Exception e) {
                fail();
            }
        }


        @Test
        void builder_can_be_made() {
            try {
                Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                        new Store(1L, "Dairy", new ArrayList<>()), 200000,
                        null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                FridgeItems items = FridgeItems
                        .builder()
                        .fridge(new Fridge())
                        .item(item)
                        .purchaseDate(LocalDateTime.now())
                        .expirationDate(LocalDateTime.now())
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class Null_columns_constructors {

        @Test
        void item_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                FridgeItems fridgeItem = new FridgeItems(null, null, new Fridge(), 1, LocalDateTime.now(), LocalDateTime.now());
            });
        }

        @Test
        void fridge_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                FridgeItems fridgeItem = new FridgeItems(null, new Item(), null, 1, LocalDateTime.now(), LocalDateTime.now());
            });
        }

        @Test
        void purchase_date_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                FridgeItems fridgeItem = new FridgeItems(null, new Item(), new Fridge(), 1, null, LocalDateTime.now());
            });
        }

        @Test
        void expiration_date_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                FridgeItems fridgeItem = new FridgeItems(null, new Item(), new Fridge(), 1, LocalDateTime.now(), null);
            });
        }

    }

    @Nested
    class Null_variables{

        @Test
        void item_cannot_be_set_to_null(){
            FridgeItems fridgeItem = new FridgeItems(null, new Item(), new Fridge(), 1, LocalDateTime.now(), LocalDateTime.now());
            assertThrows(NullPointerException.class, () -> {
                fridgeItem.setItem(null);
            });
        }

        @Test
        void fridge_cannot_be_set_to_null(){
            FridgeItems fridgeItem = new FridgeItems(null, new Item(), new Fridge(), 1, LocalDateTime.now(), LocalDateTime.now());
            assertThrows(NullPointerException.class, () -> {
                fridgeItem.setFridge(null);
            });
        }

        @Test
        void purchase_date_cannot_be_set_to_null(){
            FridgeItems fridgeItem = new FridgeItems(null, new Item(), new Fridge(), 1, LocalDateTime.now(), LocalDateTime.now());
            assertThrows(NullPointerException.class, () -> {
                fridgeItem.setPurchaseDate(null);
            });
        }

        @Test
        void expiration_date_cannot_be_set_to_null(){
            FridgeItems fridgeItem = new FridgeItems(null, new Item(), new Fridge(), 1, LocalDateTime.now(), LocalDateTime.now());
            assertThrows(NullPointerException.class, () -> {
                fridgeItem.setExpirationDate(null);
            });
        }
    }

    @Nested
    class Getters{

        @Test
        void item_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, "picture.png", "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            FridgeItems fridgeItem = new FridgeItems(null, item, fridge, 1, LocalDateTime.now(), LocalDateTime.now());
            assertEquals(item, fridgeItem.getItem());
        }

        @Test
        void fridge_items_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, "picture.png", "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            FridgeItems fridgeItem = new FridgeItems(null, item, fridge, 1, LocalDateTime.now(), LocalDateTime.now());
            assertEquals(fridge, fridgeItem.getFridge());
        }

        @Test
        void quantity_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, "picture.png", "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            FridgeItems fridgeItem = new FridgeItems(null, item, fridge, 1, LocalDateTime.now(), LocalDateTime.now());
            assertEquals(1, fridgeItem.getQuantity());
        }

        @Test
        void purchase_date_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            LocalDateTime purchaseDate = LocalDateTime.now();
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, "picture.png", "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            FridgeItems fridgeItem = new FridgeItems(null, item, fridge, 1, purchaseDate, LocalDateTime.now());
            assertEquals(purchaseDate, fridgeItem.getPurchaseDate());
        }

        @Test
        void expiration_date_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            LocalDateTime expirationDate = LocalDateTime.now();
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, "picture.png", "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            FridgeItems fridgeItem = new FridgeItems(null, item, fridge, 1, LocalDateTime.now(), expirationDate);
            assertEquals(expirationDate, fridgeItem.getExpirationDate());
        }

    }

}