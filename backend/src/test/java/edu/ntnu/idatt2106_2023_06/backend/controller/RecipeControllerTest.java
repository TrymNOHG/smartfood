package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.RecipeShoppingDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipePartDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.recipe.RecipeSuggestionAddDTO;
import edu.ntnu.idatt2106_2023_06.backend.filter.JwtAuthenticationFilter;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Day;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Recipe;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipePart;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

        mockMvc.perform(post("/recipe/suggestion/accept")
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
        Long recipeId = 1L;
        Long userId = 1L;
        Long fridgeId = 1L;

        mockMvc.perform(post("/recipe/suggestion/deny")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("recipe", recipeId.toString())
                        .param("user", userId.toString())
                        .param("fridge", fridgeId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddSuggestion() throws Exception {
        RecipeSuggestionAddDTO recipeSuggestionAddDTO = new RecipeSuggestionAddDTO(1L, 1l, 1L);

        mockMvc.perform(post("/recipe/suggestion/add")
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

    @Test
    public void testLoadRecipe() throws Exception {
        // Set up mock data
        RecipePartDTO recipePartDTO = new RecipePartDTO("Salt",   new ArrayList<>());
        RecipeLoadDTO recipeLoadDTO = RecipeLoadDTO.builder()
                .recipeId(1L)
                .recipeName("Spaghetti Bolognese")
                .description("A classic Italian dish")
                .author("Jamie Oliver")
                .servingSize(4)
                .difficulty(2)
                .allergens(new ArrayList<>())
                .instructions(new ArrayList<>())
                .recipeParts(List.of(recipePartDTO))
                .build();

        when(recipeService.getRecipe(any(String.class))).thenReturn(recipeLoadDTO);

        // Perform GET request to controller endpoint
        mockMvc.perform(get("/recipe/get")
                        .param("recipe", "Spaghetti Bolognese")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName").value(recipeLoadDTO.getRecipeName()))
                .andExpect(jsonPath("$.description").value(recipeLoadDTO.getDescription()))
                .andExpect(jsonPath("$.recipeParts", hasSize(1)));
    }

    @Test
    public void testLoadRecipeByName() throws Exception {
        // Mock the service layer
        RecipePartDTO recipePartDTO = new RecipePartDTO("Salt",   new ArrayList<>());

        RecipeLoadDTO recipeLoadDTO = RecipeLoadDTO.builder()
                .recipeId(1L)
                .recipeName("test")
                .description("A classic Italian dish")
                .author("Jamie Oliver")
                .servingSize(4)
                .difficulty(2)
                .allergens(new ArrayList<>())
                .instructions(new ArrayList<>())
                .recipeParts(List.of(recipePartDTO))
                .build();
        Page<RecipeLoadDTO> recipeLoadDTOs = new PageImpl<>(Collections.singletonList(recipeLoadDTO));
        when(recipeService.getRecipesByName(anyString(), anyInt(), anyInt())).thenReturn(recipeLoadDTOs);

        // Send the request
        mockMvc.perform(get("/recipe/load")
                        .param("recipe", "test")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].recipeName").value("test"));

        // Verify the service layer was called with the correct arguments
        verify(recipeService).getRecipesByName("test", 0, 10);
    }

    @Test
    public void testLoadRecipeByFridgeItems() throws Exception {
        // Mock the service layer
        RecipePartDTO recipePartDTO = new RecipePartDTO("Salt",   new ArrayList<>());

        RecipeLoadDTO recipeLoadDTO = RecipeLoadDTO.builder()
                .recipeId(1L)
                .recipeName("test")
                .description("A classic Italian dish")
                .author("Jamie Oliver")
                .servingSize(4)
                .difficulty(2)
                .allergens(new ArrayList<>())
                .instructions(new ArrayList<>())
                .recipeParts(List.of(recipePartDTO))
                .build();

        Page<RecipeLoadDTO> recipeLoadDTOs = new PageImpl<>(Collections.singletonList(recipeLoadDTO));
        when(recipeService.getRecipesByFridgeId(anyLong(), anyInt(), anyInt())).thenReturn(recipeLoadDTOs);

        // Send the request
        mockMvc.perform(get("/recipe/loadByFridge")
                        .param("fridge", "1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].recipeName").value("test"));

        // Verify the service layer was called with the correct arguments
        verify(recipeService).getRecipesByFridgeId(1L, 0, 10);
    }

    @Test
    public void testLoadRecipeByFridgeItemsAndDay() throws Exception {
        // Mock the service layer
        RecipePartDTO recipePartDTO = new RecipePartDTO("Salt",   new ArrayList<>());

        RecipeLoadDTO recipeLoadDTO = RecipeLoadDTO.builder()
                .recipeId(1L)
                .recipeName("test")
                .description("A classic Italian dish")
                .author("Jamie Oliver")
                .servingSize(4)
                .difficulty(2)
                .allergens(new ArrayList<>())
                .instructions(new ArrayList<>())
                .recipeParts(List.of(recipePartDTO))
                .build();
        Page<RecipeLoadDTO> recipeLoadDTOs = new PageImpl<>(Collections.singletonList(recipeLoadDTO));
        when(recipeService.getRecipesByFridgeIdAndDay(anyLong(), anyInt(), anyInt(), any(Day.class))).thenReturn(recipeLoadDTOs);

        // Send the request
        mockMvc.perform(get("/recipe/loadByDay")
                        .param("fridge", "1")
                        .param("day", "MONDAY")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].recipeName").value("test"));

        // Verify the service layer was called with the correct arguments
        verify(recipeService).getRecipesByFridgeIdAndDay(1L, 0, 10, Day.MONDAY);
    }

    @Test
    public void testGenerateItemRecipeScores() throws Exception {
        // Send the request
        mockMvc.perform(post("/recipe/generateScores"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void testAddIngredientToShoppingList() throws Exception {
        // Arrange
        RecipeShoppingDTO recipeShoppingDTO = new RecipeShoppingDTO(1L, new ArrayList<>());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Act
        mockMvc.perform(post("/recipe/addIngredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recipeShoppingDTO))
                        .with(authentication(authentication)))
                .andExpect(status().isOk());

        // Assert
        verify(itemService, times(1)).addIngredientsToShoppingList(recipeShoppingDTO, "user1");
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
