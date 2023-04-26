package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.users.Token;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
public class TokenTest {

    private Token token;

    @BeforeEach
    public void setUp() {
        token = Token.builder()
                .tokenId(1L)
                .token("randomToken")
                .timeCreated(LocalDateTime.of(2023, 5, 1, 12, 0))
                .timeExpires(LocalDateTime.of(2023, 5, 1, 13, 0))
                .timeConfirmed(LocalDateTime.of(2023, 5, 1, 13, 0))
                .user(User
                        .builder()
                        .username("Ole123")
                        .firstName("Ole")
                        .lastName("Norman")
                        .password("123123123")
                        .email("Ole@gmail.com")
                        .build())
                .build();
    }

    @Nested
    class Token_object_with {

        @Test
        public void no_arg_constructor_can_be_made() {
            try {
                Token token = new Token();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        public void all_arg_constructor_can_be_made() {
            try {
                User user = User
                        .builder()
                        .username("Ole123")
                        .firstName("Ole")
                        .lastName("Norman")
                        .password("123123123")
                        .email("Ole@gmail.com")
                        .build();
                Token token = new Token(1L, "randomToken", LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                        LocalDateTime.now(), user);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        public void required_args_constructor_can_be_made() {
            try {
                User user = User
                        .builder()
                        .username("Ole123")
                        .firstName("Ole")
                        .lastName("Norman")
                        .password("123123123")
                        .email("Ole@gmail.com")
                        .build();
                Token token = new Token("randomToken", LocalDateTime.now(),
                        LocalDateTime.now().plusHours(1), user);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        public void builder_can_be_made() {
            try {
                User user = User
                        .builder()
                        .username("Ole123")
                        .firstName("Ole")
                        .lastName("Norman")
                        .password("123123123")
                        .email("Ole@gmail.com")
                        .build();
                Token token = Token.builder()
                        .token("randomToken")
                        .timeCreated(LocalDateTime.now())
                        .timeExpires(LocalDateTime.now().plusHours(1))
                        .timeConfirmed(LocalDateTime.now())
                        .user(user)
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class When_setting_Token_variables {


        @Test
        public void setting_Token_to_null_throws_exception() {
            assertThrows(NullPointerException.class, () -> {
                token.setToken(null);
            });
        }

        @Test
        public void setting_TimeCreated_to_null_throws_exception() {
            assertThrows(NullPointerException.class, () -> {
                token.setTimeCreated(null);
            });
        }

        @Test
        public void setting_TimeExpires_to_null_throws_exception() {
            assertThrows(NullPointerException.class, () -> {
                token.setTimeExpires(null);
            });
        }

        @Test
        public void setting_TimeConfirmed_to_null_throws_exception() {
            try {
                token.setTimeConfirmed(null);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        public void setting_User_to_null_throws_exception() {
            assertThrows(NullPointerException.class, () -> {
                token.setUser(null);
            });
        }

    }

    @Nested
    class Token_can_properly_get {

        @Test
        void token_id() {
            Long expectedId = 1L;
            Long actualId = token.getTokenId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void token() {
            String expectedToken = "randomToken";
            String actualToken = token.getToken();

            assertEquals(expectedToken, actualToken);
        }

        @Test
        void timeCreated() {
            LocalDateTime expectedTimeCreated = LocalDateTime.of(2023, 5, 1, 12, 0);
            LocalDateTime actualTimeCreated = token.getTimeCreated();

            assertEquals(expectedTimeCreated, actualTimeCreated);
        }

        @Test
        void timeExpires() {
            LocalDateTime expectedTimeExpires = LocalDateTime.of(2023, 5, 1, 13, 0);
            LocalDateTime actualTimeExpires = token.getTimeExpires();

            assertEquals(expectedTimeExpires, actualTimeExpires);
        }

        @Test
        void timeConfirmed() {
            LocalDateTime expectedTimeConfirmed = LocalDateTime.of(2023, 5, 1, 13, 0);
            LocalDateTime actualTimeConfirmed = token.getTimeConfirmed();

            assertEquals(expectedTimeConfirmed, actualTimeConfirmed);
        }

        @Test
        void user() {
            User expectedUser = User
                    .builder()
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            User actualUser = token.getUser();

            assertEquals(expectedUser, actualUser);
        }

    }


}
