package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FridgeMemberTest {

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