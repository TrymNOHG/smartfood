package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

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
                FridgeMember fridgeMember = new FridgeMember(null, new User(), new Fridge(), true);
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
    class Null_columns_constructors {

        @Test
        void user_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                FridgeMember fridgeMember = new FridgeMember(null, null, new Fridge(), true);
            });
        }

        @Test
        void fridge_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                FridgeMember fridgeMember = new FridgeMember(null, new User(), null, true);
            });
        }

    }

    @Nested
    class Null_variables{

        @Test
        void user_cannot_be_set_to_null(){
            FridgeMember fridgeMember = new FridgeMember(null, new User(), new Fridge(), true);
            assertThrows(NullPointerException.class, () -> {
                fridgeMember.setUser(null);
            });
        }

        @Test
        void fridge_cannot_be_set_to_null(){
            FridgeMember fridgeMember = new FridgeMember(null, new User(), new Fridge(), true);
            assertThrows(NullPointerException.class, () -> {
                fridgeMember.setFridge(null);
            });
        }
    }

    @Nested
    class Getters{

        @Test
        void user_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com", new HashSet<>(), new HashSet<>(), new HashSet<>(), new ArrayList<>(), null);
            FridgeMember fridgeMember = new FridgeMember(null, user, fridge, true);
            assertEquals(user, fridgeMember.getUser());
        }

        @Test
        void fridge_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com", new HashSet<>(), new HashSet<>(), new HashSet<>(), new ArrayList<>(), null);
            FridgeMember fridgeMember = new FridgeMember(null, user, fridge, true);
            assertEquals(fridge, fridgeMember.getFridge());
        }

        @Test
        void super_user_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>(), new ArrayList<>());
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com", new HashSet<>(), new HashSet<>(), new HashSet<>(), new ArrayList<>(), null);
            FridgeMember fridgeMember = new FridgeMember(null, user, fridge, true);
            assertEquals(true, fridgeMember.isSuperUser());
        }
    }

}