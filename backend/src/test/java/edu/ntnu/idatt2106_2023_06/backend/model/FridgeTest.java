package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FridgeTest {

    @Nested
    class Fridge_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                Fridge fridge = new Fridge();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                Fridge fridge = new Fridge(1L, "Fridge");
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void required_args_constructor_can_be_made() {
            try {
                Fridge fridge = new Fridge("Fridge");
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                Fridge fridge = Fridge
                        .builder()
                        .fridgeId(1L)
                        .fridgeName("Fridge")
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

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