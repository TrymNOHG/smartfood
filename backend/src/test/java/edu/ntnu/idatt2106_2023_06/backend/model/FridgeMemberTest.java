package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FridgeMemberTest {

    @Nested
    class FridgeMember_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                FridgeMember fridgeMember = new FridgeMember();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                FridgeMember fridgeMember = new FridgeMember(null, new User(), new Fridge());
            } catch (Exception e) {
                fail();
            }
        }


        @Test
        void builder_can_be_made() {
            try {
                FridgeMember fridgeMember = FridgeMember
                        .builder()
                        .fridge(new Fridge())
                        .user(new User())
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

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
                Item item = new Item(1L, "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()), 200000, new Date(), new Date(), null);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void required_args_constructor_can_be_made() {
            try {
                Item item = new Item("Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.", new Store(1L, "Dairy", new ArrayList<>()), 200000);
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
                        .briefDesc("Tine melk kommer fra fri gående, grass matet kuer.")
                        .store(new Store(1L, "Dairy", new ArrayList<>()))
                        .price(200000)
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class Null_columns_constructors {

        @Test
        void user_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                FridgeMember fridgeMember = new FridgeMember(null, null, new Fridge());
            });
        }

        @Test
        void fridge_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                FridgeMember fridgeMember = new FridgeMember(null, new User(), null);
            });
        }

    }

    @Nested
    class Null_variables{

        @Test
        void user_cannot_be_set_to_null(){
            FridgeMember fridgeMember = new FridgeMember(null, new User(), new Fridge());
            assertThrows(NullPointerException.class, () -> {
                fridgeMember.setUser(null);
            });
        }

        @Test
        void fridge_cannot_be_set_to_null(){
            FridgeMember fridgeMember = new FridgeMember(null, new User(), new Fridge());
            assertThrows(NullPointerException.class, () -> {
                fridgeMember.setFridge(null);
            });
        }
    }

    @Nested
    class Getters{

        @Test
        void user_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family");
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com");
            FridgeMember fridgeMember = new FridgeMember(null, user, fridge);
            assertEquals(user, fridgeMember.getUser());
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