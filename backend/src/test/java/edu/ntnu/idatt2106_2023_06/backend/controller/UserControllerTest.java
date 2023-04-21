package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.security.AuthenticationResponseDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoginDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserRegisterDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.User;
import edu.ntnu.idatt2106_2023_06.backend.service.files.FileStorageService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.AuthenticationService;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void registerNewUser() throws Exception {
        // Arrange
        UserRegisterDTO user = new UserRegisterDTO("username", "password", "Ole", "Brumm", "ole.brumm@gmail.com");

        // Act
        MvcResult mvcResult = mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String response = mvcResult.getResponse().getContentAsString();
        AuthenticationResponseDTO authenticationResponse = objectMapper.readValue(response, AuthenticationResponseDTO.class);
        assertNotNull(authenticationResponse.token());
    }

    @Test
    public void registerNewUserWithExistingUsername() throws Exception {
        // Arrange
        UserRegisterDTO user1 = new UserRegisterDTO("testUsername", "password", "Ola", "Nordmann", "ola.nordmann@gmail.com");
        UserRegisterDTO user2 = new UserRegisterDTO("testUsername", "password", "Geir", "Smith", "geir@gmail.com");

        // Act
        MvcResult mvcResult = mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult2 = mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Assert
        String response1 = mvcResult.getResponse().getContentAsString();
        AuthenticationResponseDTO authenticationResponse1 = objectMapper.readValue(response1, AuthenticationResponseDTO.class);
        assertNotNull(authenticationResponse1.token());

        String response2 = mvcResult2.getResponse().getContentAsString();
        assertTrue(response2.contains("Time of error: "));
    }

    @Test
    public void registerNewUserWithExistingEmail() throws Exception {
        // Arrange
        UserRegisterDTO user1 = new UserRegisterDTO("testUsername1", "password", "Ola", "Nordmann", "geir@gmail.com");
        UserRegisterDTO user2 = new UserRegisterDTO("testUsername2", "password", "Geir", "Smith", "geir@gmail.com");

        // Act
        MvcResult mvcResult = mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult2 = mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Assert
        String response1 = mvcResult.getResponse().getContentAsString();
        AuthenticationResponseDTO authenticationResponse1 = objectMapper.readValue(response1, AuthenticationResponseDTO.class);
        assertNotNull(authenticationResponse1.token());

        String response2 = mvcResult2.getResponse().getContentAsString();
        assertTrue(response2.contains("Time of error: "));
    }

    @Test
    public void userLogin() throws Exception {
        // Arrange
        UserRegisterDTO user = new UserRegisterDTO("testUsername1", "password", "Ola", "Nordmann", "geir@gmail.com");
        authenticationService.register(user);

        UserLoginDTO authenticationRequest = new UserLoginDTO("geir@gmail.com", "password");

        // Act
        MvcResult mvcResult = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String response = mvcResult.getResponse().getContentAsString();
        AuthenticationResponseDTO authenticationResponse = objectMapper.readValue(response, AuthenticationResponseDTO.class);
        assertNotNull(authenticationResponse.token());
    }

    @Test
    public void userLoginWithWrongPassword() throws Exception {
        // Arrange
        UserRegisterDTO user = new UserRegisterDTO("testUsername1", "password", "Ola", "Nordmann", "ola.nordmann@gmail.com");
        authenticationService.register(user);

        UserLoginDTO authenticationRequest = new UserLoginDTO("ola.nordmann@gmail.com", "wrongPassword");

        // Act
        MvcResult mvcResult = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Assert
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains("Time of error: "));
    }

    @Test
    public void userLoginWithWrongEmail() throws Exception {
        // Arrange
        UserRegisterDTO user = new UserRegisterDTO("testUsername1", "password", "Ola", "Nordmann", "ola.nordmann@gmail.com");
        authenticationService.register(user);

        UserLoginDTO authenticationRequest = new UserLoginDTO("wrong@wrongmail.wrong", "password");

        // Act
        MvcResult mvcResult = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains("Time of error: "));
    }

    @Test
    public void updateUserInfo() throws Exception {
        // Arrange
        UserRegisterDTO user = new UserRegisterDTO("oldUsername", "password", "Ola", "Nordmann", "ola.nordmann@gmail.com");
        authenticationService.register(user);

        UserLoginDTO authenticationRequest = new UserLoginDTO("ola.nordmann@gmail.com", "password");
        MvcResult mvcResult = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        AuthenticationResponseDTO authenticationResponse = objectMapper.readValue(response, AuthenticationResponseDTO.class);
        assertNotNull(authenticationResponse.token());

        UserUpdateDTO userUpdate = new UserUpdateDTO("newUsername", "newFirstName", "newLastName", "newEmail@msn.ru");

        // Act
        mockMvc.perform(put("/user/update/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + authenticationResponse.token())
                        .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        UserLoadDTO changedUser = userService.loadUserDTOByUsername("newUsername");
        assertEquals("newUsername", changedUser.username());
        assertEquals("newFirstName", changedUser.firstName());
        assertEquals("newLastName", changedUser.lastName());
        assertEquals("newEmail@msn.ru", changedUser.email());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserDTOByUsername("oldUsername"));
    }

    @Test
    public void updateUserPicture() throws Exception {
        // Arrange
        UserRegisterDTO user = new UserRegisterDTO("testUsername", "password", "Ola", "Nordmann", "ola.nordmann@gmail.com");
        authenticationService.register(user);

        UserLoginDTO authenticationRequest = new UserLoginDTO("ola.nordmann@gmail.com", "password");
        MvcResult mvcResult = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        AuthenticationResponseDTO authenticationResponse = objectMapper.readValue(response, AuthenticationResponseDTO.class);
        assertNotNull(authenticationResponse.token());

        Resource testImageResource = new ClassPathResource("test-image.jpg");
        MockMultipartFile testImageFile = new MockMultipartFile("picture",
                "test-image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                testImageResource.getInputStream());

        // Act
        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/update/picture")
                        .file(testImageFile)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .header("Authorization", "Bearer " + authenticationResponse.token())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        long userId = userService.loadUserDTOByUsername("testUsername").userId();
        byte[] image = fileStorageService.getProfilePicture(userId);
        assertArrayEquals(testImageFile.getBytes(), image);
    }

    @Test
    public void updateUserPassword() throws Exception {
        // Arrange
        UserRegisterDTO user = new UserRegisterDTO("testUsername", "password", "Ola", "Nordmann", "test@test.test");
        authenticationService.register(user);

    }
}
