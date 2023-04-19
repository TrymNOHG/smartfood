package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingItemsTest {

    @Nested
    class ShoppingItems_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                ShoppingItems item = new ShoppingItems();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, " +
                        "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()), 200000,
                        new Date(), new Date(), null);
                ShoppingItems shoppingItems = new ShoppingItems(null, item,
                        new Fridge(1L, "Fridge"), true);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, " +
                        "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()), 200000,
                        new Date(), new Date(), null);
                ShoppingItems shoppingItem = ShoppingItems
                        .builder()
                        .item(item)
                        .id(null)
                        .suggestion(true)
                        .fridge(new Fridge(1L, "Fridge"))
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
                ShoppingItems shoppingItems = new ShoppingItems(null, null, new Fridge(), true);
            });
        }

        @Test
        void fridge_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), null, true);
            });
        }

    }

    @Nested
    class Null_variables{

        @Test
        void item_cannot_be_set_to_null(){
            ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), new Fridge(), true);
            assertThrows(NullPointerException.class, () -> {
                shoppingItems.setItem(null);
            });
        }

        @Test
        void fridge_cannot_be_set_to_null(){
            ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), new Fridge(), true);
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
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date(), null);
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, true);
            assertEquals(item, shoppingItems.getItem());
        }

        @Test
        void fridge_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family");
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date(), null);
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, true);
            assertEquals(fridge, shoppingItems.getFridge());
        }

        @Test
        void suggestion_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family");
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date(), null);
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, true);
            assertEquals(true, shoppingItems.isSuggestion());
        }
    }

    @Nested
    class Setters{
        @Test
        void item_setter_can_be_applied(){
            Fridge fridge = new Fridge(1L, "Norman family");
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date(), null);
            ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), fridge, true);

            shoppingItems.setItem(item);
            assertEquals(item, shoppingItems.getItem());
        }

        @Test
        void fridge_setter_can_be_applied(){
            Fridge fridge = new Fridge(1L, "Norman family");
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date(), null);
            ShoppingItems shoppingItems = new ShoppingItems(null, item, new Fridge(), true);

            shoppingItems.setFridge(fridge);
            assertEquals(fridge, shoppingItems.getFridge());
        }

        @Test
        void suggestion_setter_can_be_applied(){
            Fridge fridge = new Fridge(1L, "Norman family");
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, new Date(), new Date(), null);
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, true);

            shoppingItems.setSuggestion(true);

            assertEquals(true, shoppingItems.isSuggestion());
        }
    }

}