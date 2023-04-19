package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FridgeTest {

    @Nested
    class Fridge_can_properly_get {
        Fridge getFridge() {
            return new Fridge(1L, "Norman family");
        }

        @Test
        void id() {
            Fridge fridge = getFridge();
            Long expectedId = 1L;
            Long actualId = fridge.getFridgeId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void name_of_fridge() {
            Fridge fridge = getFridge();
            String expectedName = "Norman family";

            String actualName = fridge.getFridgeName();

            assertEquals(expectedName, actualName);
        }

    }

    @Nested
    class Fridge_can_properly_set {
        Fridge getFridge() {
            return new Fridge(1L, "Norman family");
        }

        @Test
        void fridge_name() {
            Fridge fridge = getFridge();
            String expectedName = "Norman family";

            fridge.setFridgeName(expectedName);
            String actualFridgeName = fridge.getFridgeName();

            assertEquals(expectedName, actualFridgeName);
        }

    }

}