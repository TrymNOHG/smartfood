package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatAddItemToFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.stat.StatDeleteFromFridgeDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeMemberId;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.StatType;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeMemberRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.fridge.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatTypeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.security.AuthenticationService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import edu.ntnu.idatt2106_2023_06.backend.service.stat.StatService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class StatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Autowired
    private JwtService jwtService;


    private String token;
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
        token = jwtService.generateToken(userDetails);
    }

    @Test
    void addDeleteItemStat() throws Exception {
        StatDeleteFromFridgeDTO statDeleteFromFridgeDTO = StatDeleteFromFridgeDTO.builder()
                .amountDeleted(69)
                .itemName("item")
                .storeName("store")
                .fridgeId(1L)
                .build();

        mockMvc.perform(post("/stat/add/delete-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statDeleteFromFridgeDTO)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(statRepository.findAllByFridge(1L).get(0).getStatValue(), 69.0);
    }

    @Test
    void addBoughtItemTest() throws Exception {
        StatAddItemToFridgeDTO statAddItemToFridgeDTO = StatAddItemToFridgeDTO.builder()
                .price(69.0)
                .itemName("item")
                .storeName("store")
                .fridgeId(1L)
                .build();

        mockMvc.perform(post("/stat/add/bought-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statAddItemToFridgeDTO)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(statRepository.findAllByFridge(1L).get(0).getStatValue(), 69.0);
    }
}
