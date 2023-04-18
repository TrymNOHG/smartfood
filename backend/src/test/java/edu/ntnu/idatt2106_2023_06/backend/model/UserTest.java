package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Nested
    class When_User_constructor_has_non_null_variable {

        @Test
        public void username_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User(null, null, "Ole", "Norman",
                        "password", "Ole@gmail.com", new Date(), "+47 12345678");
            });
        }
        @Test
        public void password_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User(null, "Ole123", "Ole", "Norman",
                        null, "Ole@gmail.com", new Date(), "+47 12345678");
            });
        }

        @Test
        public void first_name_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User(null, "Ole123", null, "Norman",
                        "password","Ole@gmail.com", new Date(), "+47 12345678");
            });
        }
        @Test
        public void last_name_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User(null, "Ole123", "Ole", null,
                        "password","Ole@gmail.com", new Date(), "+47 12345678");
            });
        }

        @Test
        public void email_throws_NullPointer_Exception_if_null() {
            assertThrows(NullPointerException.class, () -> {
                User user = new User(null, "Ole123", "Ole", "Norman",
                        "password",null, new Date(), "+47 12345678");
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
                    "password","Ole@gmail.com", new Date(), "+4712345678");
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

        @Test
        void birthDate() {
            User user = getUser();
            Date expectedDate = new Date();
            user.setBirthDate(expectedDate);

            Date actualDate = user.getBirthDate();

            assertEquals(expectedDate, actualDate);
        }

        @Test
        void phone() {
            User user = getUser();
            String expectedPhone = "+4712345678";
            user.setPhone(expectedPhone);

            String actualPhone = user.getPhone();

            assertEquals(expectedPhone, actualPhone);
        }

    }

}