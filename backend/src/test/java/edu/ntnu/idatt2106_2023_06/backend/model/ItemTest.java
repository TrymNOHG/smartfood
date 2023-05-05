package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.ShoppingItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Store;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Nested
    class Item_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                Item item = new Item();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                        new Store(1L, "Dairy", new ArrayList<>()), 200000,
                        null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void required_args_constructor_can_be_made() {
            try {
                Item item = new Item("Tine Melk", new Store(1L, "Dairy", new ArrayList<>()), 200000);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                Item item = Item
                        .builder()
                        .itemId(1L)
                        .productName("Tine Melk")
                        .desc("Tine melk kommer fra fri gående, grass matet kuer.")
                        .store(new Store(1L, "Dairy", new ArrayList<>()))
                        .price(200000)
                        .ean("123456")
                        .amount(100.0)
                        .unit("ml")
                        .expiresIn(4)
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class Null_columns_constructors {

        @Test
        void product_name_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                Item item = new Item(1L, null, "Tine melk kommer fra fri gående, grass matet kuer.",
                        new Store(1L, "Dairy", new ArrayList<>()), 200000,
                        null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            });
        }

//        @Test
//        void brief_description_cannot_be_null(){
//            assertThrows(NullPointerException.class, () -> {
//                Item item = new Item(1L, "Tine melk", null, new Store(1L, "Dairy",
//                        new ArrayList<>()), 200000, new Date(), new Date(), null, new ArrayList<>(),
//                        new ArrayList<>(), new ArrayList<>());
//            });
//        }

        @Test
        void store_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                        null, 200000, null, "12345678", 100.0, "ml", 4, new ArrayList<>(),
                        new ArrayList<>(), new ArrayList<>());
            });
        }

    }

    @Nested
    class Null_variables{


        @Test
        void product_name_cannot_be_set_to_null(){
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml",4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            assertThrows(NullPointerException.class, () -> {
                item.setProductName(null);
            });
        }

//        @Test
//        void brief_description_cannot_be_set_to_null(){
//            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.",
//                    new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(),
//                    null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//            assertThrows(NullPointerException.class, () -> {
//                item.setDesc(null);
//            });
//        }

        @Test
        void store_cannot_be_set_to_null(){
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(1L, "Dairy", new ArrayList<>()), 200000,
                    null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            assertThrows(NullPointerException.class, () -> {
                item.setStore(null);
            });
        }

    }

    @Nested
    class Getters{

        @Test
        void item_id_getter_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    store, 200000, null, "12345678", 100.0, "ml", 4, new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>());
            Long expectedItemId = 1L;

            assertEquals(expectedItemId, item.getItemId());
        }

        @Test
        void product_name_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", store,
                    200000, null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            String expectedProductName = "Tine melk";

            assertEquals(expectedProductName, item.getProductName());
        }

        @Test
        void brief_description_getter_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    store, 200000, null, "12345678", 100.0, "ml",4,  new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            String expectedBriefDesc = "Tine melk kommer fra fri gående, grass matet kuer.";

            assertEquals(expectedBriefDesc, item.getDesc());
        }

        @Test
        void price_getter_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", store,
                    200000, null, "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            double expectedPrice = 200000;

            assertEquals(expectedPrice, item.getPrice());
        }

        @Test
        void picture_getter_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", store,
                    200000, "picture.png", "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            String expectedPictureLink = "picture.png";

            assertEquals(expectedPictureLink, item.getPictureLink());
        }

        @Test
        void ean_getter_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", store,
                    200000, "picture.png", "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            String expectedEAN = "12345678";

            assertEquals(expectedEAN, item.getEan());
        }

        @Test
        void amount_getter_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", store,
                    200000, "picture.png", "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            double expectedAmount = 100.0;

            assertEquals(expectedAmount, item.getAmount());
        }

        @Test
        void unit_getter_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", store,
                    200000, "picture.png", "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            String expectedUnit = "ml";

            assertEquals(expectedUnit, item.getUnit());
        }

        @Test
        void expires_in_getter_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", store,
                    200000, "picture.png", "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            int expectedExpiresIn = 4;

            assertEquals(expectedExpiresIn, item.getExpiresIn());
        }

        @Test
        void recipe_items_getter_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", store,
                    200000, "picture.png", "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            List<RecipeItems> expectedItems = new ArrayList<>();

            assertEquals(expectedItems, item.getItemsInRecipe());
        }

        @Test
        void shopping_items_getter_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", store,
                    200000, "picture.png", "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            List<ShoppingItems> expectedItems = new ArrayList<>();

            assertEquals(expectedItems, item.getItemsInShoppingList());
        }

        @Test
        void fridge_items_getter_returns_correct_value(){
            Store store = new Store(1L, "Dairy", new ArrayList<>());
            Item item = new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", store,
                    200000, "picture.png", "12345678", 100.0, "ml", 4, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            List<FridgeItems> expectedItems = new ArrayList<>();

            assertEquals(expectedItems, item.getItemsInFridge());
        }

    }


}