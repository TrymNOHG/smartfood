package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemMoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.shopping_list.ShoppingListLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.filter.JwtAuthenticationFilter;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
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
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ShoppingItemsControllerTest {

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
    public void testAddToShoppingList() throws Exception {
        ItemDTO itemDTO = new ItemDTO( "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                "Kiwi", 200000, null, 1, false);
        Long fridgeId = 1L;
        boolean suggestion = false;

        Item item = new Item();

        when(userService.isSuperUser(fridgeId, user.getUsername())).thenReturn(true);
        when(itemService.addItem(itemDTO)).thenReturn(item);


        doNothing().when(itemService).addToShoppingList(itemDTO, fridgeId, suggestion);
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
        List<ShoppingListLoadDTO> shoppingItems = new ArrayList<>();
        shoppingItems.add(new ShoppingListLoadDTO(1L, "Tine Melk",
                "Tine melk kommer fra fri gående, grass matet kuer.", "Kiwi", 200000,
                null, 1, true));
        when(itemService.getShoppingListItems(fridgeId)).thenReturn(shoppingItems);
        mockMvc.perform(MockMvcRequestBuilders.get("/item/shopping/get")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fridgeId", fridgeId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteItemFromShoppingList() throws Exception {
        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
        boolean suggestion = false;

        when(userService.isUserInFridge(itemRemoveDTO.fridgeId(), user.getUsername())).thenReturn(true);
        doNothing().when(itemService).removeItemFromShoppingList(itemRemoveDTO, suggestion);


        mockMvc.perform(MockMvcRequestBuilders.delete("/item/shopping/delete")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("suggestion", Boolean.toString(suggestion))
                        .content(asJsonString(itemRemoveDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testBuyItemsFromShoppingList() throws Exception {
        ItemMoveDTO itemRemoveDTO = new ItemMoveDTO(1L, 1L);
        List<ItemMoveDTO> itemDTOList = List.of(itemRemoveDTO);

        when(userService.isSuperUser(itemRemoveDTO.fridgeId(), user.getUsername())).thenReturn(true);


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

        when(userService.isSuperUser(itemRemoveDTO.fridgeId(), user.getUsername())).thenReturn(true);


        mockMvc.perform(MockMvcRequestBuilders.post("/item/shopping/suggestion")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRemoveDTO)))
                .andExpect(status().isOk());

        verify(itemService, times(1)).acceptSuggestion(itemRemoveDTO);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
