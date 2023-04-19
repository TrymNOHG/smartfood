package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Nested
    class User_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                User user = new User();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                User user = new User(null, "Ole123", "Ole", "Norman",
                        "password", "Ole@gmail.com");
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void required_args_constructor_can_be_made() {
            try {
                User user = new User( "Ole123", "Ole", "Norman",
                        "password", "Ole@gmail.com");
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                User user = User
                        .builder()
                        .username("Ole123")
                        .firstName("Ole")
                        .lastName("Norman")
                        .password("123123123")
                        .email("Ole@gmail.com")
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class When_User_constructor_has_non_null_variable {

        @Test
        public void username_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User(null, null, "Ole", "Norman",
                        "password", "Ole@gmail.com");
            });
        }
        @Test
        public void password_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User(null, "Ole123", "Ole", "Norman",
                        null, "Ole@gmail.com");
            });
        }

        @Test
        public void first_name_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User(null, "Ole123", null, "Norman",
                        "password","Ole@gmail.com");
            });
        }
        @Test
        public void last_name_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User(null, "Ole123", "Ole", null,
                        "password","Ole@gmail.com");
            });
        }

        @Test
        public void email_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User(null, "Ole123", "Ole", "Norman",
                        "password",null);
            });
        }

    }

    @Nested
    class When_setting_User_non_null_variable {
        @Test
        public void username_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User();
                user.setUsername(null);
            });
        }
        @Test
        public void password_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User();
                user.setPassword(null);
            });
        }

        @Test
        public void first_name_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User();
                user.setFirstName(null);
            });
        }
        @Test
        public void last_name_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User();
                user.setLastName(null);
            });
        }

        @Test
        public void email_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User();
                user.setEmail(null);
            });
        }

    }

    @Nested
    class User_can_properly_get {
        User getUser() {
            return new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com");
        }

        @Test
        void username() {
            User user = getUser();
            String expectedUsername = "Ole123";
            String actualUsername = user.getUsername();

            assertEquals(expectedUsername, actualUsername);
        }

        @Test
        void password() {
            User user = getUser();
            String expectedPassword = "password";
            String actualPassword = user.getPassword();

            assertEquals(expectedPassword, actualPassword);
        }

        @Test
        void firstName() {
            User user = getUser();
            String expectedFullName = "Ole";
            String actualFullName = user.getFirstName();

            assertEquals(expectedFullName, actualFullName);
        }
        @Test
        void lastName() {
            User user = getUser();
            String expectedFullName = "Norman";
            String actualFullName = user.getLastName();

            assertEquals(expectedFullName, actualFullName);
        }

        @Test
        void email() {
            User user = getUser();
            String expectedEmail = "Ole@gmail.com";
            String actualEmail = user.getEmail();

            assertEquals(expectedEmail, actualEmail);
        }



    }

}