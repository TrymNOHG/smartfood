package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
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
                        new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                        null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                FridgeItems items = new FridgeItems(null, item, new Fridge(), 1);
            } catch (Exception e) {
                fail();
            }
        }


        @Test
        void builder_can_be_made() {
            try {
                Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                        new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
                        null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                FridgeItems items = FridgeItems
                        .builder()
                        .fridge(new Fridge())
                        .item(item)
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
                FridgeItems fridgeItem = new FridgeItems(null, null, new Fridge(), 1);
            });
        }

        @Test
        void fridge_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                FridgeItems fridgeItem = new FridgeItems(null, new Item(), null, 1);
            });
        }

    }

    @Nested
    class Null_variables{

        @Test
        void item_cannot_be_set_to_null(){
            FridgeItems fridgeItem = new FridgeItems(null, new Item(), new Fridge(), 1);
            assertThrows(NullPointerException.class, () -> {
                fridgeItem.setItem(null);
            });
        }

        @Test
        void fridge_cannot_be_set_to_null(){
            FridgeItems fridgeItem = new FridgeItems(null, new Item(), new Fridge(), 1);
            assertThrows(NullPointerException.class, () -> {
                fridgeItem.setFridge(null);
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
                    200000, new Date(), new Date(), "picture.png", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            FridgeItems fridgeItem = new FridgeItems(null, item, fridge, 1);
            assertEquals(item, fridgeItem.getItem());
        }

        @Test
        void fridge_items_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date(), "picture.png", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            FridgeItems fridgeItem = new FridgeItems(null, item, fridge, 1);
            assertEquals(fridge, fridgeItem.getFridge());
        }

    }

}