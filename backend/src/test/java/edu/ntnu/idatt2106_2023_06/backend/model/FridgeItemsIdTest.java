package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FridgeItemsIdTest {

    @Nested
    class Constructor_test{

        @Test
        void empty_constructor_does_not_throw_errors(){
            assertDoesNotThrow(() -> {
                new FridgeItemsId();
            });
        }

        @Test
        void constructor_does_not_throw_errors(){
            assertDoesNotThrow(() -> {
                new FridgeItemsId(1L, 1L);
            });
        }
    }

}