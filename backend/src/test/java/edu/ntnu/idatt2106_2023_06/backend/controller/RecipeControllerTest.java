package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.RecipeShoppingDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeSuggestionAddDTO;
import edu.ntnu.idatt2106_2023_06.backend.filter.JwtAuthenticationFilter;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemRecipeScoreService;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import edu.ntnu.idatt2106_2023_06.backend.service.items.RecipeService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRecipeScoreService itemRecipeScoreService;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private ItemService itemService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    private String jwt;
    private User user;

    @BeforeEach
    public void setUp() {
        try {
            MockitoAnnotations.openMocks(this);

            user = User
                    .builder()
                    .userId(1L)
                    .username("OleN")
                    .password("password")
                    .firstName("Ole")
                    .lastName("Norman")
                    .email("test@gamil.com")
                    .build();

            userRepository.save(user);

            jwt = jwtService.generateToken(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Handle any exceptions here
        }
    }

    @Test
    public void testAcceptSuggestion() throws Exception {
        RecipeShoppingDTO recipeShoppingDTO = new RecipeShoppingDTO(1L, new ArrayList<>());
        Long recipeId = 1L;
        Long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/suggestion/accept")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipeShoppingDTO))
                        .param("recipe", recipeId.toString())
                        .param("user", userId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDenySuggestion() throws Exception {
        RecipeShoppingDTO recipeShoppingDTO = new RecipeShoppingDTO(1L, new ArrayList<>());
        Long recipeId = 1L;
        Long userId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/suggestion/deny")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipeShoppingDTO))
                        .param("recipe", recipeId.toString())
                        .param("user", userId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddSuggestion() throws Exception {
        RecipeSuggestionAddDTO recipeSuggestionAddDTO = new RecipeSuggestionAddDTO(1L, 1l, 1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/suggestion/add")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipeSuggestionAddDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoadSuggestion() throws Exception {
        Long fridgeId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/suggestion/load")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .param("fridge", fridgeId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
