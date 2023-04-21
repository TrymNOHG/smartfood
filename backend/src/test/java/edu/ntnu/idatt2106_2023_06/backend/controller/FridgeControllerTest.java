package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.*;
import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeMemberRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FridgeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FridgeService fridgeService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FridgeRepository fridgeRepository;

    @Autowired
    private FridgeMemberRepository fridgeMemberRepository;

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


    @Nested
    class AddUserToFridgeTests {

        @Test
        public void shouldReturnOkWhenAddingUserToFridge() throws Exception {
            FridgeUserDTO fridgeUserDTO = new FridgeUserDTO(1L, "OleN", true);

            mockMvc.perform(MockMvcRequestBuilders.post("/fridge/add/user")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(fridgeUserDTO)))
                    .andExpect(status().isOk());
        }

        @Test
        public void shouldReturnUnauthorizedWhenJwtIsMissing() throws Exception {
            FridgeUserDTO fridgeUserDTO = new FridgeUserDTO(1L, "OleN", true);

            mockMvc.perform(MockMvcRequestBuilders.post("/fridge/add/user")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(fridgeUserDTO)))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        public void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/fridge/add/user")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(new Fridge())))
                    .andExpect(status().isBadRequest());
        }

    }

    @Nested
    class DeleteUserFromFridgeTests {

        @Test
        public void shouldReturnOkWhenDeletingUserFromFridge() throws Exception {
            FridgeUserDTO fridgeUserDTO = new FridgeUserDTO(1L, "user1", true);

            mockMvc.perform(MockMvcRequestBuilders.delete("/fridge/delete/user")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(fridgeUserDTO)))
                    .andExpect(status().isOk());
        }

        @Test
        public void shouldReturnUnauthorizedWhenJwtIsMissing() throws Exception {
            FridgeUserDTO fridgeUserDTO = new FridgeUserDTO(1L, "user1", true);

            mockMvc.perform(MockMvcRequestBuilders.delete("/fridge/delete/user")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(fridgeUserDTO)))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        public void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/fridge/delete/user")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(new Fridge())))
                    .andExpect(status().isBadRequest());
        }

    }

    @Nested
    class UpdateUserInFridgeTests {

        @Test
        public void shouldReturnOkWhenUpdatingUserInFridge() throws Exception {
            FridgeUserDTO fridgeUserDTO = new FridgeUserDTO(1L, "user1", true);

            mockMvc.perform(MockMvcRequestBuilders.put("/fridge/update/user")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(fridgeUserDTO)))
                    .andExpect(status().isOk());
        }

        @Test
        public void shouldReturnUnauthorizedWhenJwtIsMissing() throws Exception {
            FridgeUserDTO fridgeUserDTO = new FridgeUserDTO(1L, "user1", true);

            mockMvc.perform(MockMvcRequestBuilders.put("/fridge/update/user")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(fridgeUserDTO)))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        public void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.put("/fridge/update/user")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(new Fridge())))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class Fridge_can_be {

        @Test
        void created_by_user() throws Exception {
            // Arrange
            String username = "user";
            int expectedStatus = 200;
            doNothing().when(fridgeService).createFridge("Home Fridge", username);
            FridgeDTO fridgeDTO = new FridgeDTO(1L, "Home fridge");
            // Act
            MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/fridge/create")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .param("fridgeName", fridgeDTO.fridgeName()))
                    .andReturn();

            // Assert
            Assertions.assertEquals(expectedStatus, response.getResponse().getStatus());

        }

        @Test
        void updated_by_user() throws Exception {
            // Arrange
            String username = "user";
            int expectedStatus = 200;

            Authentication authentication = mock(Authentication.class);

            when(authentication.getName()).thenReturn("user");

            doNothing().when(fridgeService).updateFridgeName(new FridgeDTO(1L, "Home Fridge"), "user");
            FridgeDTO fridgeDTO = new FridgeDTO(1L, "Home fridge");
            // Act
            ObjectMapper objectMapper = new ObjectMapper();
            MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/fridge/update")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(fridgeDTO)))
                    .andReturn();

            // Assert
            Assertions.assertEquals(expectedStatus, response.getResponse().getStatus());

        }

    }

    @Nested
    class Fridges_can_load {

        @Test
        public void ids_by_user() throws Exception {
            // Arrange
            String username = "user";
            int expectedStatus = 200;
            List<Long> fridgeIds = new ArrayList<>();
            fridgeIds.add(1L);
            when(fridgeService.retrieveFridgeIdsByUsername(username)).thenReturn(fridgeIds);

            // Act
            MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/fridge/loadAllId")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .param("user", username))
                    .andReturn();

            // Assert
            Assertions.assertEquals(expectedStatus, response.getResponse().getStatus());

            ObjectMapper objectMapper = new ObjectMapper();
            List<Long> returnedFridgeIds = objectMapper.readValue(response.getResponse().getContentAsString(),
                    new TypeReference<List<Long>>(){});
            Assertions.assertEquals(fridgeIds, returnedFridgeIds);

        }


        @Test
        void by_user() throws Exception {

            // Arrange
            String username = "user";
            int expectedStatus = 200;
            List<FridgeDTO> fridges = new ArrayList<>();
            fridges.add(new FridgeDTO(1L, "Home Fridge"));
            FridgeLoadAllDTO fridgeLoadAllDTO = new FridgeLoadAllDTO(fridges);
            when(fridgeService.retrieveFridgesByUsername(username)).thenReturn(fridgeLoadAllDTO);

            // Act
            MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/fridge/loadAll")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .param("user", username))
                    .andReturn();

            // Assert
            Assertions.assertEquals(expectedStatus, response.getResponse().getStatus());

            ObjectMapper objectMapper = new ObjectMapper();
            FridgeLoadAllDTO returnedFridges = objectMapper.readValue(response.getResponse().getContentAsString(),
                    new TypeReference<FridgeLoadAllDTO>(){});
            Assertions.assertEquals(fridgeLoadAllDTO, returnedFridges);

        }

        @Test
        void users_by_fridge_id() throws Exception {

            // Arrange
            String username = "user";
            int expectedStatus = 200;

            Authentication authentication = mock(Authentication.class);

            when(authentication.getName()).thenReturn("user");

            List<FridgeMemberLoadDTO> members = new ArrayList<>();
            members.add(FridgeMemberLoadDTO
                    .builder()
                            .userId(1L)
                            .email("t@t.com")
                            .firstName("Hans")
                            .lastName("Olsen")
                            .username("Hans123")
                            .isSuperUser(true)
                    .build());

            FridgeMemberLoadAllDTO fridgeMembers = FridgeMemberLoadAllDTO
                    .builder()
                    .memberInfo(members)
                    .build();

            when(fridgeService.retrieveMembersByFridgeId(1L, "user")).thenReturn(fridgeMembers);

            // Act
            MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/fridge/loadAllUsers")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .param("fridgeId", "1"))
                    .andReturn();

            // Assert
            Assertions.assertEquals(expectedStatus, response.getResponse().getStatus());

        }

    }

}
