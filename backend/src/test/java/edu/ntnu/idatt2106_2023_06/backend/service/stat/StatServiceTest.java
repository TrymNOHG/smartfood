package edu.ntnu.idatt2106_2023_06.backend.service.stat;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeMemberId;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.StatType;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.Statistics;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeMemberRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatTypeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.json.Json;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class StatServiceTest {

    @Nested
    @SpringBootTest
    class AddStats {

        @Autowired
        private StatService statService;

        @Autowired
        private StatRepository statRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private FridgeRepository fridgeRepository;

        @Autowired
        private FridgeMemberRepository fridgeMemberRepository;

        @Autowired
        private StatTypeRepository statTypeRepository;

        @BeforeEach
        @Transactional
        void setUp() {
            // setup stat types
            statTypeRepository.save(new StatType(1L, "Waste",
                    "This entry contains the amount of times food was thrown",new ArrayList<>()));
            statTypeRepository.save(new StatType(2L, "tes2t","test2", new ArrayList<>()));
            statTypeRepository.save(new StatType(3L, "test3","test3", new ArrayList<>()));


            User user = User
                    .builder()
                    .userId(1L)
                    .username("Ole123")
                    .firstName("Ole")
                    .lastName("Norman")
                    .password("123123123")
                    .email("Ole@gmail.com")
                    .build();
            userRepository.save(user);
            Fridge fridge = new Fridge(1L, "Test Fridge", new HashSet<>(), new ArrayList<>(),
                    new ArrayList<>(), new HashSet<>(), new ArrayList<>());

            fridgeRepository.save(fridge);
            fridgeMemberRepository.save(new FridgeMember(new FridgeMemberId(1L, 1L), user, fridge,true));

            UserDetails userDetails = userDetailsService.loadUserByUsername("Ole123");
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
        }

        @Test
        void addDeleteStat() {
            StatDeleteFromFridgeDTO statDeleteFromFridgeDTO = StatDeleteFromFridgeDTO.builder()
                    .amountDeleted(100)
                    .itemName("item")
                    .storeName("store")
                    .fridgeId(1L)
                    .build();

            Logger logger = org.slf4j.LoggerFactory.getLogger(StatService.class);
            logger.info(statTypeRepository.findAll().toString());
            statService.statDeleteItemFromFridge(statDeleteFromFridgeDTO);

            assertEquals(1, statRepository.findAll().size());
            assertEquals(statRepository.findAllByFridge(1L).get(0).getStatValue(), 100);
        }

        @Test
        void addDeleteStatMultipleTimes() throws JsonProcessingException {
            StatDeleteFromFridgeDTO statDeleteFromFridgeDTO = StatDeleteFromFridgeDTO.builder()
                    .amountDeleted(100)
                    .itemName("item")
                    .storeName("store")
                    .fridgeId(1L)
                    .build();

            statService.statDeleteItemFromFridge(statDeleteFromFridgeDTO);
            statService.statDeleteItemFromFridge(statDeleteFromFridgeDTO);
            statService.statDeleteItemFromFridge(statDeleteFromFridgeDTO);

            assertEquals(3, statRepository.findAll().size());
            assertEquals(statRepository.findAllByFridge(1L).get(0).getStatValue(), 100);
        }

        @Test
        void statAddItemToFridgeTest() {
            StatAddItemToFridgeDTO statAddItemToFridgeDTO = StatAddItemToFridgeDTO.builder()
                    .price(69.0)
                    .itemName("item")
                    .storeName("store")
                    .fridgeId(1L)
                    .build();

            statService.statAddItemToFridge(statAddItemToFridgeDTO);
            statService.statAddItemToFridge(statAddItemToFridgeDTO);
            statService.statAddItemToFridge(statAddItemToFridgeDTO);

            assertEquals(3, statRepository.findAll().size());
            assertEquals(statRepository.findAllByFridge(1L).get(0).getStatValue(), 69.0);
        }

        @Test
        void getMoneyUsedPerDayUserTest() throws JsonProcessingException {
            StatAddItemToFridgeDTO statAddItemToFridgeDTO = StatAddItemToFridgeDTO.builder()
                    .price(69.0)
                    .itemName("item")
                    .storeName("store")
                    .fridgeId(1L)
                    .build();
            statService.statAddItemToFridge(statAddItemToFridgeDTO);

            String json = statService.getMoneyUsedPerDayUser();

            Logger logger = org.slf4j.LoggerFactory.getLogger(StatService.class);
            logger.info(json);
            assertTrue(json.contains("69"));
            assertTrue(json.contains(LocalDate.now().toString()));
        }

        @Test
        void getMoneyUsedPerDayUserMultipleTest() throws JsonProcessingException {
            StatAddItemToFridgeDTO statAddItemToFridgeDTO = StatAddItemToFridgeDTO.builder()
                    .price(69.0)
                    .itemName("item")
                    .storeName("store")
                    .fridgeId(1L)
                    .build();
            statService.statAddItemToFridge(statAddItemToFridgeDTO);
            statService.statAddItemToFridge(statAddItemToFridgeDTO);

            String json = statService.getMoneyUsedPerDayUser();

            Logger logger = org.slf4j.LoggerFactory.getLogger(StatService.class);
            logger.info(json);
            assertTrue(json.contains(Integer.toString(69*2)));
            assertTrue(json.contains(LocalDate.now().toString()));
        }

        @Test
        void getMoneyUsedPerDayFridgeTest() throws JsonProcessingException {
            StatAddItemToFridgeDTO statAddItemToFridgeDTO = StatAddItemToFridgeDTO.builder()
                    .price(69.0)
                    .itemName("item")
                    .storeName("store")
                    .fridgeId(1L)
                    .build();
            statService.statAddItemToFridge(statAddItemToFridgeDTO);

            String json = statService.getMoneyUsedPerDayFridge(1L);

            Logger logger = org.slf4j.LoggerFactory.getLogger(StatService.class);
            logger.info(json);
            assertTrue(json.contains("69"));
            assertTrue(json.contains(LocalDate.now().toString()));
        }

        @Test
        void getMoneyUsedPerDayFridgeMultipleTest() throws JsonProcessingException {
            StatAddItemToFridgeDTO statAddItemToFridgeDTO = StatAddItemToFridgeDTO.builder()
                    .price(69.0)
                    .itemName("item")
                    .storeName("store")
                    .fridgeId(1L)
                    .build();
            statService.statAddItemToFridge(statAddItemToFridgeDTO);
            statService.statAddItemToFridge(statAddItemToFridgeDTO);

            String json = statService.getMoneyUsedPerDayFridge(1L);

            Logger logger = org.slf4j.LoggerFactory.getLogger(StatService.class);
            logger.info(json);
            assertTrue(json.contains(Integer.toString(69*2)));
            assertTrue(json.contains(LocalDate.now().toString()));
        }

    }
}