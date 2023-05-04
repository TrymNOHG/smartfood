package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

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
                         null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                ShoppingItems shoppingItems = new ShoppingItems(null, item,
                        new Fridge(1L, "Fridge", new HashSet<>(),
                                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()), new User(), true, 1);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, " +
                        "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()), 200000,
                         null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                ShoppingItems shoppingItem = ShoppingItems
                        .builder()
                        .item(item)
                        .id(null)
                        .suggestion(true)
                        .fridge(new Fridge(1L, "Fridge", new HashSet<>(),
                                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()))
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
                ShoppingItems shoppingItems = new ShoppingItems(null, null, new Fridge(), new User(), true, 1);
            });
        }

        @Test
        void fridge_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), null, new User(), true, 1);
            });
        }

    }

    @Nested
    class Null_variables{

        @Test
        void item_cannot_be_set_to_null(){
            ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), new Fridge(), new User(), true, 1);
            assertThrows(NullPointerException.class, () -> {
                shoppingItems.setItem(null);
            });
        }

        @Test
        void fridge_cannot_be_set_to_null(){
            ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), new Fridge(), new User(), true, 1);
            assertThrows(NullPointerException.class, () -> {
                shoppingItems.setFridge(null);
            });
        }
    }

    @Nested
    class Getters{

        @Test
        void item_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, new User(), true, 1);
            assertEquals(item, shoppingItems.getItem());
        }

        @Test
        void fridge_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, new User(), true, 1);
            assertEquals(fridge, shoppingItems.getFridge());
        }

        @Test
        void user_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            User user = new User();
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, user, true, 1);
            assertEquals(user, shoppingItems.getUser());
        }


        @Test
        void suggestion_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, new User(), true,1 );
            assertEquals(true, shoppingItems.isSuggestion());
        }
    }

    @Nested
    class Setters{
        @Test
        void item_setter_can_be_applied(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            ShoppingItems shoppingItems = new ShoppingItems(null, new Item(), fridge, new User(),true, 1);

            shoppingItems.setItem(item);
            assertEquals(item, shoppingItems.getItem());
        }

        @Test
        void fridge_setter_can_be_applied(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            ShoppingItems shoppingItems = new ShoppingItems(null, item, new Fridge(), new User(), true, 1);

            shoppingItems.setFridge(fridge);
            assertEquals(fridge, shoppingItems.getFridge());
        }

        @Test
        void user_setter_can_be_applied(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            User user = new User();
            ShoppingItems shoppingItems = new ShoppingItems(null, item, new Fridge(), new User(), true, 1);

            shoppingItems.setUser(user);
            assertEquals(user, shoppingItems.getUser());
        }

        @Test
        void suggestion_setter_can_be_applied(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, " +
                    "grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()),
                    200000, null, "12345678", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            ShoppingItems shoppingItems = new ShoppingItems(null, item, fridge, new User(), true, 1);

            shoppingItems.setSuggestion(true);

            assertEquals(true, shoppingItems.isSuggestion());
        }
    }

}