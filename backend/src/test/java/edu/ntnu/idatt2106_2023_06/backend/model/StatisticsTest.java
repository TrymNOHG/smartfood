package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.StatType;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.Statistics;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {


    @Nested
    class Statistics_object_with {

        @Test
        void no_arg_constructor_can_be_made() {
            try {
                Statistics statistics = new Statistics();
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void all_arg_constructor_can_be_made() {
            try {
                Statistics statistics = new Statistics(1L, new User(), new Fridge(),
                        new StatType(), 1.0, "Meny", "Melk", LocalDateTime.now(), 1);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void required_arg_constructor_can_be_made() {
            try {
                Statistics statistics = new Statistics(new StatType(), 1.0, "Meny", "Melk",  LocalDateTime.now(), 1);
            } catch (Exception e) {
                fail();
            }
        }

        @Test
        void builder_can_be_made() {
            try {
                Statistics statistics = Statistics
                        .builder()
                        .fridge(new Fridge())
                        .user(new User())
                        .statId(1L)
                        .statType(new StatType())
                        .statValue(1.0)
                        .storeName("Meny")
                        .itemName("Melk")
                        .timestamp(LocalDateTime.now())
                        .quantity(1)
                        .build();
            } catch (Exception e) {
                fail();
            }
        }

    }

    @Nested
    class Null_columns_constructors {

        @Test
        void stat_type_cannot_be_null(){
            assertThrows(NullPointerException.class, () -> {
                Statistics statistics = new Statistics(1L, new User(), new Fridge(),
                        null, 1.0, "Meny", "Melk", LocalDateTime.now(), 1);
            });
        }

        @Test
        void timestamp_cannot_be_null() {
            assertThrows(NullPointerException.class, () -> {
                Statistics statistics = new Statistics(1L, new User(), new Fridge(),
                        new StatType(), 1.0,"Meny", "Melk", null, 1);
            });
        }


    }

    @Nested
    class Null_variables{

        @Test
        void user_can_be_set_to_null(){
            Statistics statistics = new Statistics(1L, new User(), new Fridge(),
                    new StatType(), 1.0,"Meny", "Melk", LocalDateTime.now(), 1);
            statistics.setUser(null);

            assertNull(statistics.getUser());
        }

        @Test
        void fridge_can_be_set_to_null(){
            Statistics statistics = new Statistics(1L, new User(), new Fridge(),
                    new StatType(), 1.0,"Meny", "Melk", LocalDateTime.now(), 1);

            statistics.setFridge(null);

            assertNull(statistics.getFridge());
        }

        @Test
        void stat_type_cannot_be_set_to_null() {
            Statistics statistics = new Statistics(1L, new User(), new Fridge(),
                    new StatType(), 1.0,"Meny", "Melk", LocalDateTime.now(), 1);
            assertThrows(NullPointerException.class, () -> {
               statistics.setStatType(null);
            });
        }

        @Test
        void store_name_cannot_be_set_to_null() {
            Statistics statistics = new Statistics(1L, new User(), new Fridge(),
                    new StatType(), 1.0,"Meny", "Melk", LocalDateTime.now(), 1);
            assertThrows(NullPointerException.class, () -> {
                statistics.setStoreName(null);
            });
        }

        @Test
        void item_name_cannot_be_set_to_null() {
            Statistics statistics = new Statistics(1L, new User(), new Fridge(),
                    new StatType(), 1.0,"Meny", "Melk", LocalDateTime.now(), 1);
            assertThrows(NullPointerException.class, () -> {
                statistics.setItemName(null);
            });
        }

        @Test
        void timestamp_cannot_be_set_to_null() {
            Statistics statistics = new Statistics(1L, new User(), new Fridge(),
                    new StatType(), 1.0,"Meny", "Melk", LocalDateTime.now(), 1);
            assertThrows(NullPointerException.class, () -> {
                statistics.setTimestamp(null);
            });
        }

    }

    @Nested
    class Getters{

        @Test
        void stat_id_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com", new HashSet<>(), new HashSet<>());
            StatType statType = new StatType(1L, "Waste",
                    "This entry contains the amount of times food was thrown",new ArrayList<>());
            Statistics statistics = new Statistics(1L, user, fridge,
                    statType, 1.0,"Meny", "Melk", LocalDateTime.now(), 1);
            assertEquals(1L, statistics.getStatId());
        }


        @Test
        void user_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com", new HashSet<>(), new HashSet<>());
            Statistics statistics = new Statistics(1L, user, fridge,
                    new StatType(), 1.0,"Meny", "Melk", LocalDateTime.now(), 1);
            assertEquals(user, statistics.getUser());
        }

        @Test
        void fridge_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com", new HashSet<>(), new HashSet<>());
            Statistics statistics = new Statistics(1L, user, fridge,
                    new StatType(), 1.0,"Meny", "Melk", LocalDateTime.now(), 1);
            assertEquals(fridge, statistics.getFridge());
        }

        @Test
        void stat_type_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com", new HashSet<>(), new HashSet<>());
            StatType statType = new StatType(1L, "Waste",
                    "This entry contains the amount of times food was thrown",new ArrayList<>());
            Statistics statistics = new Statistics(1L, user, fridge,
                    statType, 1.0,"Meny", "Melk", LocalDateTime.now(), 1);
            assertEquals(statType, statistics.getStatType());
        }

        @Test
        void stat_value_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com", new HashSet<>(), new HashSet<>());
            StatType statType = new StatType(1L, "Waste",
                    "This entry contains the amount of times food was thrown",new ArrayList<>());
            Statistics statistics = new Statistics(1L, user, fridge,
                    statType, 1.0,"Meny", "Melk", LocalDateTime.now(), 1);
            assertEquals(1.0, statistics.getStatValue());
        }

        @Test
        void store_name_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com", new HashSet<>(), new HashSet<>());
            StatType statType = new StatType(1L, "Waste",
                    "This entry contains the amount of times food was thrown",new ArrayList<>());
            Statistics statistics = new Statistics(1L, user, fridge,
                    statType, 1.0,"Meny", "Melk", LocalDateTime.now(), 1);
            assertEquals("Meny", statistics.getStoreName());
        }

        @Test
        void item_name_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com", new HashSet<>(), new HashSet<>());
            StatType statType = new StatType(1L, "Waste",
                    "This entry contains the amount of times food was thrown",new ArrayList<>());
            Statistics statistics = new Statistics(1L, user, fridge,
                    statType, 1.,"Meny", "Melk", LocalDateTime.now(), 1);
            assertEquals("Melk", statistics.getItemName());
        }

        @Test
        void timestamp_getter_returns_correct_value(){
            Fridge fridge = new Fridge(1L, "Norman family", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>());
            User user = new User(null, "Ole123", "Ole", "Norman",
                    "password","Ole@gmail.com", new HashSet<>(), new HashSet<>());
            StatType statType = new StatType(1L, "Waste",
                    "This entry contains the amount of times food was thrown",new ArrayList<>());
            LocalDateTime timestamp = LocalDateTime.now();
            Statistics statistics = new Statistics(1L, user, fridge,
                    statType, 1.,"Meny", "Melk", timestamp, 1);
            assertEquals(timestamp, statistics.getTimestamp());
        }
    }

}