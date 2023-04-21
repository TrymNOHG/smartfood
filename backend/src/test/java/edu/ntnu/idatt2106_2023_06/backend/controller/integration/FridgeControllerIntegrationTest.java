package edu.ntnu.idatt2106_2023_06.backend.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.fridge.FridgeUserDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeMemberRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.FridgeRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FridgeControllerIntegrationTest {

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
        void testDeleteUserFromFridge() {
//            User user = new User();
//            user.setUserId(1L);
//
//            Fridge fridge = new Fridge();
//            fridge.setFridgeId(1L);
//            fridge.getUsers().add(user);
//
//            FridgeUserDTO fridgeUserDTO = new FridgeUserDTO();
//            fridgeUserDTO.setUserId(user.getUserId());
//
//            Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
//            Mockito.when(fridgeRepository.findById(fridge.getFridgeId())).thenReturn(Optional.of(fridge));
//
//            Fridge updatedFridge = fridgeService.deleteUserFromFridge(fridgeUserDTO);
//
//            assertFalse(updatedFridge.getUsers().contains(user));
        }

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
        void testUpdateUserInFridge() {
//            User user = new User();
//            user.setUserId(1L);
//
//            Fridge fridge = new Fridge();
//            fridge.setFridgeId(1L);
//            fridge.getUsers().add(user);
//
//            FridgeUserDTO fridgeUserDTO = new FridgeUserDTO();
//            fridgeUserDTO.setUserId(user.getUserId());
//            fridgeUserDTO.setIsOwner(false);
//
//            Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
//            Mockito.when(fridgeRepository.findById(fridge.getFridgeId())).thenReturn(Optional.of(fridge));
//
//            Fridge updatedFridge = fridgeService.updateUserInFridge(fridgeUserDTO);
//
//            assertFalse(updatedFridge.getOwner().equals(user));
        }

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

}
