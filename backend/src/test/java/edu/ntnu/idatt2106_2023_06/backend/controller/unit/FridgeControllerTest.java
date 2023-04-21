package edu.ntnu.idatt2106_2023_06.backend.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.controller.FridgeController;
import edu.ntnu.idatt2106_2023_06.backend.controller.ItemController;
import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeUserDTO;
import edu.ntnu.idatt2106_2023_06.backend.filter.JwtAuthenticationFilter;
import edu.ntnu.idatt2106_2023_06.backend.model.User;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FridgeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FridgeService fridgeService;

    @Mock
    private FridgeController fridgeController;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private User user;
    private String jwt;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new FridgeController(fridgeService)).build();
    }

    @Test
    @DisplayName("Test Add User To Fridge Success")
    public void testAddUserToFridgeSuccess() throws Exception {
//        // Mock the input data
////        FridgeUserDTO fridgeUserDTO = new FridgeUserDTO("fridge-123", "newuser");
//
//        // Mock the authentication object
//        Authentication authentication = null;
//
//        // Mock the fridgeService
//        FridgeService fridgeService = mock(FridgeService.class);
//
//        // Create the controller instance and call the method
//        FridgeController fridgeController = new FridgeController(fridgeService);
//        ResponseEntity<Object> response = fridgeController.addUserToFridge(fridgeUserDTO, authentication);
//
//        // Verify that the fridgeService method was called
//        verify(fridgeService).addUserToFridge(fridgeUserDTO, null);
//
//        // Verify that the response is OK
//        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Test Add User To Fridge Failure")
    public void testAddUserToFridgeFailure() throws Exception {
        // given
        FridgeUserDTO fridgeUserDTO = new FridgeUserDTO(1L, "user", true);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password");

        // when
        doNothing().when(fridgeService).addUserToFridge(fridgeUserDTO, "user");
        RequestBuilder requestBuilder = post("/fridge/add/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(fridgeUserDTO));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assert (response.getStatus() == 401);
    }
}
