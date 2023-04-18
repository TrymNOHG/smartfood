package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FridgeItemsTest {

    @Nested
    class Null_columns_constructors {

        @Test
        void item_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                FridgeItems fridgeItem = new FridgeItems(null, null, new Fridge());
            });
        }

        @Test
        void fridge_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                FridgeItems fridgeItem = new FridgeItems(null, new Item(), null);
            });
        }

    }

    @Nested
    class Null_variables{

        @Test
        void item_cannot_be_set_to_null(){
            FridgeItems fridgeItem = new FridgeItems(null, new Item(), new Fridge());
            assertThrows(NullPointerException.class, () -> {
                fridgeItem.setItem(null);
            });
        }

        @Test
        void fridge_cannot_be_set_to_null(){
            FridgeItems fridgeItem = new FridgeItems(null, new Item(), new Fridge());
            assertThrows(NullPointerException.class, () -> {
                fridgeItem.setFridge(null);
            });
        }
    }

    @Nested
    class Getters{

        @Test
        void item_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family");
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new FoodCategory(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date());
            FridgeItems fridgeItem = new FridgeItems(null, item, fridge);
            assertEquals(item, fridgeItem.getItem());
        }

        @Test
        void fridge_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family");
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com");
            FridgeMember fridgeMember = new FridgeMember(null, user, fridge);
            assertEquals(fridge, fridgeMember.getFridge());
        }
    }

}