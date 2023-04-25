package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.filter.JwtAuthenticationFilter;

import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.store.StoreRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ItemControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @MockBean
    private FridgeService fridgeService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ItemRepository itemRepository;

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
    public void testAddToFridge() throws Exception {
        ItemDTO itemDTO = new ItemDTO( "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                "Kiwi", 200000, new Date(), new Date(),
                null, 1, false);
        Long fridgeId = 1L;

        given(itemService.addItem(itemDTO)).willReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/item/fridge/add")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemDTO))
                        .param("fridgeId", fridgeId.toString()))
                .andExpect(status().isOk());

        verify(itemService, times(1)).addItem(itemDTO);
        verify(itemService, times(1)).addToFridge(1L, fridgeId, 1);
    }

    @Test
    public void testGetFridge() throws Exception {
        Long fridgeId = 1L;
        ItemDTO itemDTO = new ItemDTO( "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                "Kiwi", 200000, new Date(), new Date(),
                null, 1, false);
        ItemDTO itemDTO2 = new ItemDTO( "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                "Kiwi", 200000, new Date(), new Date(),
                null, 1, false);
        List<ItemDTO> itemList = Arrays.asList(itemDTO, itemDTO2);

        given(itemService.getFridgeItems(fridgeId)).willReturn(itemList);

        mockMvc.perform(MockMvcRequestBuilders.get("/item/fridge/get")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .param("fridgeId", fridgeId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(itemService, times(1)).getFridgeItems(fridgeId);
    }

    @Test
    public void testDeleteItemFromFridge() throws Exception {
        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/item/fridge/delete")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRemoveDTO)))
                .andExpect(status().isOk());

        verify(itemService, times(1)).deleteItemFromFridge(itemRemoveDTO);
    }

    @Test
    public void testAddToShoppingList() throws Exception {
        ItemDTO itemDTO = new ItemDTO( "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                "Kiwi", 200000, new Date(), new Date(),
                null, 1, false);
        Long fridgeId = 1L;
        boolean suggestion = false;

        when(userService.isUserInFridge(fridgeId, user.getUsername())).thenReturn(true);
        when(itemService.addItem(itemDTO)).thenReturn(1L);


        doNothing().when(itemService).addToShoppingList(1L, fridgeId, itemDTO.quantity(), suggestion);
        mockMvc.perform(MockMvcRequestBuilders.post("/item/shopping/add")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fridgeId", fridgeId.toString())
                        .param("suggestion", Boolean.toString(suggestion))
                        .content(asJsonString(itemDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetShoppingList() throws Exception {
        Long fridgeId = 1L;
        List<ItemDTO> itemList = new ArrayList<>();
        ItemDTO itemDTO = new ItemDTO( "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                "Kiwi", 200000, new Date(), new Date(),
                null, 2, false);
        when(itemService.getShoppingListItems(fridgeId)).thenReturn(itemList);
        mockMvc.perform(MockMvcRequestBuilders.get("/item/shopping/get")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fridgeId", fridgeId.toString()))
                .andExpect(status().isOk());

    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteItemFromShoppingList() throws Exception {
        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
        boolean suggestion = false;

        when(userService.isUserInFridge(itemRemoveDTO.fridgeId(), user.getUsername())).thenReturn(true);
        doNothing().when(itemService).deleteItemFromShoppingList(itemRemoveDTO, suggestion);


        mockMvc.perform(MockMvcRequestBuilders.delete("/item/shopping/delete")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("suggestion", Boolean.toString(suggestion))
                        .content(asJsonString(itemRemoveDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testBuyItemsFromShoppingList() throws Exception {
        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
        List<ItemRemoveDTO> itemDTOList = List.of(itemRemoveDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/item/shopping/buy")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemDTOList)))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/item/shopping/buy")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemDTOList)))
                .andExpect(status().isOk());

        verify(itemService, times(2)).buyItemsFromShoppingList(itemDTOList);
    }

    @Test
    public void testAcceptSuggestion() throws Exception {
        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);

        mockMvc.perform(MockMvcRequestBuilders.post("/item/shopping/suggestion")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRemoveDTO)))
                .andExpect(status().isOk());

        verify(itemService, times(1)).acceptSuggestion(itemRemoveDTO);
    }
}
