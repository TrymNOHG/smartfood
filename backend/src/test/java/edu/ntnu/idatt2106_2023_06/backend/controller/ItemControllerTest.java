package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.controller.ItemController;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.filter.JwtAuthenticationFilter;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ItemController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class ItemControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private JwtAuthenticationFilter authenticationFilter;

    @Test
    public void testAddToFridge() throws Exception {
        ItemDTO itemDTO = new ItemDTO( "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                "Kiwi", 200000, new Date(), new Date(),
                null, 1);
        Long fridgeId = 1L;

        given(itemService.addItem(itemDTO)).willReturn(1L);

        mockMvc.perform(post("/item/fridge/add")
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
                null, 1);
        ItemDTO itemDTO2 = new ItemDTO( "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                "Kiwi", 200000, new Date(), new Date(),
                null, 1);
        List<ItemDTO> itemList = Arrays.asList(itemDTO, itemDTO2);

        given(itemService.getFridgeItems(fridgeId)).willReturn(itemList);

        mockMvc.perform(get("/item/fridge/get")
                        .param("fridgeId", fridgeId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(itemService, times(1)).getFridgeItems(fridgeId);
    }

    @Test
    public void testDeleteItemFromFridge() throws Exception {
        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
        mockMvc.perform(delete("/item/fridge/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRemoveDTO)))
                .andExpect(status().isOk());

        verify(itemService, times(1)).deleteItemFromFridge(itemRemoveDTO);
    }

    @Test
    public void testAddToShoppingList() throws Exception {
        ItemDTO itemDTO = new ItemDTO( "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                "Kiwi", 200000, new Date(), new Date(),
                null, 1);
        Long fridgeId = 1L;
        boolean suggestion = false;
        when(itemService.addItem(itemDTO)).thenReturn(1L);
        doNothing().when(itemService).addToShoppingList(1L, fridgeId, itemDTO.quantity(), suggestion);
        mockMvc.perform(post("/item/shopping/add")
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
                null, 2);
        when(itemService.getShoppingListItems(fridgeId)).thenReturn(itemList);
        mockMvc.perform(get("/item/shopping/get")
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
        doNothing().when(itemService).deleteItemFromShoppingList(itemRemoveDTO, suggestion);
        mockMvc.perform(delete("/item/shopping/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("suggestion", Boolean.toString(suggestion))
                        .content(asJsonString(itemRemoveDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testBuyItemsFromShoppingList() throws Exception {
        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
        List<ItemRemoveDTO> itemDTOList = List.of(itemRemoveDTO);

        mockMvc.perform(post("/item/shopping/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemDTOList)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/item/shopping/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemDTOList)))
                .andExpect(status().isOk());

        verify(itemService, times(2)).buyItemsFromShoppingList(itemDTOList);
    }

    @Test
    public void testAcceptSuggestion() throws Exception {
        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);

        mockMvc.perform(post("/item/shopping/suggestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRemoveDTO)))
                .andExpect(status().isOk());

        verify(itemService, times(1)).acceptSuggestion(itemRemoveDTO);
    }
}
