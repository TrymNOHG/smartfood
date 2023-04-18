package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodCategoryTest {

    @Nested
    class FoodCategory_can_properly_get {
        FoodCategory getFoodCategory() {
            return new FoodCategory(1L, "Dairy", new ArrayList<>());
        }

        @Test
        void id() {
            FoodCategory foodCategory = getFoodCategory();
            Long expectedId = 1L;
            Long actualId = foodCategory.getCategoryId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void category_name() {
            FoodCategory foodCategory = getFoodCategory();
            String expectedCategoryName = foodCategory.getCategoryName();

            String actualCategoryName = foodCategory.getCategoryName();

            assertEquals(expectedCategoryName, actualCategoryName);
        }

    }

    @Nested
    class FoodCategory_can_properly_set {
        FoodCategory getFoodCategory() {
            return new FoodCategory(1L, "Dairy", new ArrayList<>());
        }
        @Test
        void category_name() {
            FoodCategory foodCategory = getFoodCategory();
            String expectedCategoryName = "Cheese";

            foodCategory.setCategoryName(expectedCategoryName);
            String actualCategoryName = foodCategory.getCategoryName();

            assertEquals(expectedCategoryName, actualCategoryName);
        }

        @Test
        void item_list() {
            FoodCategory foodCategory = getFoodCategory();
            List<Item> items = new ArrayList<>();
            items.add(new Item(1L, "Tine melk", "Tine melk kommer fra fri gående, grass matet kuer.", new FoodCategory(3L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date()));

            foodCategory.setItemsWithCategory(items);
            List<Item> actualItems = foodCategory.getItemsWithCategory();

            assertEquals(items, actualItems);
        }

    }

}