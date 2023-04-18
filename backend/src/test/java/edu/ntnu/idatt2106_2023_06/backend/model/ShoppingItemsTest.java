package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingItemsTest {

    @Nested
    class Null_columns_constructors {

        @Test
        void item_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                ShoppingItems shoppingItems = new ShoppingItems(null, null, new Fridge(), 1);
            });
        }

        @Test
        void fridge_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), null, 1);
            });
        }

    }

    @Nested
    class Null_variables{

        @Test
        void item_cannot_be_set_to_null(){
            ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), new Fridge(), 1);
            assertThrows(NullPointerException.class, () -> {
                shoppingItems.setItem(null);
            });
        }

        @Test
        void fridge_cannot_be_set_to_null(){
            ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), new Fridge(), 1);
            assertThrows(NullPointerException.class, () -> {
                shoppingItems.setFridge(null);
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
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, 1);
            assertEquals(item, shoppingItems.getItem());
        }

        @Test
        void fridge_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family");
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new FoodCategory(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date());
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, 1);
            assertEquals(fridge, shoppingItems.getFridge());
        }

        @Test
        void suggestion_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family");
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new FoodCategory(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date());
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, 1);
            assertEquals(1, shoppingItems.getSuggestion());
        }
    }

    @Nested
    class Setters{
        @Test
        void item_setter_can_be_applied(){
            Fridge fridge = new Fridge(1L, "Norman family");
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new FoodCategory(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date());
            ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), fridge, 1);

            shoppingItems.setItem(item);
            assertEquals(item, shoppingItems.getItem());
        }

        @Test
        void fridge_setter_can_be_applied(){
            Fridge fridge = new Fridge(1L, "Norman family");
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new FoodCategory(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date());
            ShoppingItems shoppingItems = new ShoppingItems(null, item, new Fridge(), 1);

            shoppingItems.setFridge(fridge);
            assertEquals(fridge, shoppingItems.getFridge());
        }

        @Test
        void suggestion_setter_can_be_applied(){
            Fridge fridge = new Fridge(1L, "Norman family");
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new FoodCategory(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date());
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, 1);

            shoppingItems.setSuggestion(2);

            assertEquals(2, shoppingItems.getSuggestion());
        }
    }

}