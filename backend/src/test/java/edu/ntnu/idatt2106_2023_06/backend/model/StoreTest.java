package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    @Nested
    class Store_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                Store store = new Store();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                Store store = new Store(1L, "Store", new ArrayList<>());
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                Store store = Store
                        .builder()
                        .storeId(1L)
                        .storeName("Store")
                        .itemsInStore(new ArrayList<>())
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class Store_can_properly_get {
        Store getFoodCategory() {
            return new Store(1L, "Dairy", new ArrayList<>());
        }

        @Test
        void id() {
            Store store = getFoodCategory();
            Long expectedId = 1L;
            Long actualId = store.getStoreId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void category_name() {
            Store store = getFoodCategory();
            String expectedCategoryName = store.getStoreName();

            String actualCategoryName = store.getStoreName();

            assertEquals(expectedCategoryName, actualCategoryName);
        }

    }

    @Nested
    class Store_can_properly_set {
        Store getFoodCategory() {
            return new Store(1L, "Dairy", new ArrayList<>());
        }
        @Test
        void category_name() {
            Store store = getFoodCategory();
            String expectedCategoryName = "Cheese";

            store.setStoreName(expectedCategoryName);
            String actualCategoryName = store.getStoreName();

            assertEquals(expectedCategoryName, actualCategoryName);
        }

        @Test
        void item_list() {
            Store store = getFoodCategory();
            List<Item> items = new ArrayList<>();
            items.add(new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                    new Store(3L, "Dairy", new ArrayList<>()), 200000,
                    new Date(), new Date(), null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

            store.setItemsInStore(items);
            List<Item> actualItems = store.getItemsInStore();

            assertEquals(items, actualItems);
        }

    }

}