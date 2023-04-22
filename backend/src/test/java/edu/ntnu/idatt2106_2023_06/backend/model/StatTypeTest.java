package edu.ntnu.idatt2106_2023_06.backend.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatTypeTest {

    @Nested
    class StatType_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                StatType statType = new StatType();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                StatType statType = new StatType(1L, "Waste",
                        "This entry contains the amount of times food was thrown",new ArrayList<>());
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void required_arg_constructor_can_be_made() {
            try {
                StatType statType = new StatType( "Waste");
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                StatType statType = StatType
                        .builder()
                        .statTypeId(1L)
                        .typeName("Waste")
                        .desc("This entry contains the amount of times food was thrown")
                        .statistics(new ArrayList<>())
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class StatType_can_properly_get {
        StatType getStatType() {
            return new StatType(1L, "Waste",
                    "This entry contains the amount of times food was thrown",new ArrayList<>()
            );
        }

        @Test
        void id() {
            StatType statType = getStatType();
            Long expectedId = 1L;
            Long actualId = statType.getStatTypeId();

            assertEquals(expectedId, actualId);
        }

        @Test
        void type_name() {
            StatType statType = getStatType();
            String expectedTypeName = "Waste";

            String actualTypeName = statType.getTypeName();

            assertEquals(expectedTypeName, actualTypeName);
        }

        @Test
        void desc() {
            StatType statType = getStatType();
            String expectedDesc = "This entry contains the amount of times food was thrown";

            String actualDesc = statType.getDesc();

            assertEquals(expectedDesc, actualDesc);
        }

        @Test
        void statistics() {
            StatType statType = getStatType();
            List<Statistics> expectedStatistics = new ArrayList<>();

            List<Statistics> actualStatistics = statType.getStatistics();


            assertEquals(expectedStatistics, actualStatistics);
        }

    }

    @Nested
    class Store_can_properly_set {
        StatType getStatType() {
            return new StatType(1L, "Waste",
                    "This entry contains the amount of times food was thrown",new ArrayList<>()
            );
        }

        @Test
        void type_name() {
            StatType statType = getStatType();
            String expectedTypeName = "Save";

            statType.setTypeName(expectedTypeName);
            String actualTypeName = statType.getTypeName();

            assertEquals(expectedTypeName, actualTypeName);
        }

        @Test
        void desc() {
            StatType statType = getStatType();
            String expectedDesc = "Save";

            statType.setDesc(expectedDesc);
            String actualDesc = statType.getDesc();

            assertEquals(expectedDesc, actualDesc);
        }

        @Test
        void statistics() {
            StatType statType = getStatType();
            List<Statistics> expectedStatistics = new ArrayList<>();

            statType.setStatistics(expectedStatistics);
            List<Statistics> actualStatistics = statType.getStatistics();


            assertEquals(expectedStatistics, actualStatistics);
        }

    }

}