package edu.ntnu.idatt2106_2023_06.backend.service.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.*;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.exception.exists.UserExistsException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.mapper.UserMapper;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeMemberId;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationProvider authenticationProvider;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private UserUpdateDTO userUpdateDTO;
    private UserPasswordUpdateDTO userPasswordUpdateDTO;
    private UserDeletionDTO userDeletionDTO;

    @BeforeEach
    void setUp() {
        userUpdateDTO = new UserUpdateDTO("username", "John", "Doe", "johndoe@example.com");
        userPasswordUpdateDTO = new UserPasswordUpdateDTO("oldPassword", "newPassword");
        userDeletionDTO = new UserDeletionDTO("username", "userToDelete");
    }

    @Test
    void updateUserShouldThrowUserNotFoundExceptionIfUserNotFound() {
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userUpdateDTO));
    }

    @Test
    public void testUpdateUser() throws UserNotFoundException, UserExistsException {
        long userId = 1L;
        String username = "testUser";
        String firstName = "John";
        String lastName = "Doe";
        String email = "johndoe@example.com";
        String updatedUsername = "updatedUser";
        String updatedFirstName = "Jane";
        String updatedLastName = "Doe";
        String updatedEmail = "janedoe@example.com";
        User user = User
                .builder()
                .userId(userId)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password("password")
                .build();

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(updatedUsername, updatedFirstName, updatedLastName, updatedEmail);
        Mockito.when(jwtService.getAuthenticatedUserId()).thenReturn(userId);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmail(updatedEmail)).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(jwtService.generateToken(user)).thenReturn("testToken");

        String token = userService.updateUser(userUpdateDTO);

        assertEquals("testToken", token);
        assertEquals(updatedUsername, user.getUsername());
        assertEquals(updatedFirstName, user.getFirstName());
        assertEquals(updatedLastName, user.getLastName());
        assertEquals(updatedEmail, user.getEmail());

        Mockito.verify(jwtService, Mockito.times(1)).getAuthenticatedUserId();
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(updatedEmail);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(jwtService, Mockito.times(1)).generateToken(user);
    }

    @Test
    public void testUpdateUserExists() throws UserNotFoundException, UserExistsException {
        long userId = 1L;
        String username = "testUser";
        String firstName = "John";
        String lastName = "Doe";
        String email = "johndoe@example.com";
        String updatedEmail = "janedoe@example.com";
        User user = User
                .builder()
                .userId(userId)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password("password")
                .build();
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(username, firstName, lastName, updatedEmail);
        Mockito.when(jwtService.getAuthenticatedUserId()).thenReturn(userId);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmail(updatedEmail)).thenReturn(Optional.of(new User()));

        Assertions.assertThrows(UserExistsException.class, () -> {
            userService.updateUser(userUpdateDTO);
        });
    }

    @Test
    void testUpdateUserPassword() {
        String username = "testuser";
        String oldPassword = "oldpassword";
        String newPassword = "newpassword";

        UserPasswordUpdateDTO userPasswordUpdateDTO = new UserPasswordUpdateDTO(oldPassword, newPassword);
        User user = User.builder()
                .username(username)
                .firstName("John")
                .lastName("Doe")
                .email("John.Doe@gmail.com")
                .password(passwordEncoder.encode(oldPassword) == null ? "password" : passwordEncoder.encode(oldPassword))
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedpassword");
        when(userRepository.save(user)).thenReturn(user);

        userService.updateUserPassword(userPasswordUpdateDTO, username);

        verify(userRepository, times(1)).findByUsername(username);
        verify(authenticationProvider, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userRepository, times(1)).save(user);

        assertEquals("encodedpassword", user.getPassword());
    }


    @Test
    public void testDeleteUser() {
        String username = "test_user";
        String password = "test_password";
        String userToDelete = "test_user";
        when(passwordEncoder.encode(password)).thenReturn("encodedpassword");
        UserDeletionDTO userDeletionDTO = new UserDeletionDTO(username, userToDelete);
        User user = User.builder()
                .username(username)
                .firstName("John")
                .lastName("Doe")
                .email("John.Doe@gmail.com")
                .password(passwordEncoder.encode(password))
                .build();

        User userToDeleteEntity = User.builder()
                .username(userToDelete)
                .firstName("John")
                .lastName("Doe")
                .email("John.Doe@gmail.com")
                .password(passwordEncoder.encode(password))
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(userToDelete)).thenReturn(Optional.of(userToDeleteEntity));

        userService.deleteUser(userDeletionDTO);

        verify(userRepository).delete(userToDeleteEntity);
    }


    @Test
    public void testDeleteUser_Unauthorized() {
        String username = "test_user";
        String password = "test_password";
        String userToDelete = "test_user123";
        when(passwordEncoder.encode(password)).thenReturn("encodedpassword");
        UserDeletionDTO userDeletionDTO = new UserDeletionDTO(username, userToDelete);
        User user = User.builder()
                .username(username)
                .firstName("John")
                .lastName("Doe")
                .email("John.Doe@gmail.com")
                .password(passwordEncoder.encode(password))
                .build();

        User userToDeleteEntity = User.builder()
                .username(userToDelete)
                .firstName("John")
                .lastName("Doe")
                .email("John.Doe@gmail.com")
                .password(passwordEncoder.encode(password))
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(userToDelete)).thenReturn(Optional.of(userToDeleteEntity));

        Assertions.assertThrows(UnauthorizedException.class, () -> {
            userService.deleteUser(userDeletionDTO);
        });
    }

    @Test
    public void testLoadUser() {
        // Arrange
        long userId = 123L;

        when(passwordEncoder.encode("password")).thenReturn("encodedpassword");
        User user = User.builder()
                .userId(userId)
                .username("JohnD")
                .firstName("John")
                .lastName("Doe")
                .email("John.Doe@gmail.com")
                .password(passwordEncoder.encode("password"))
                .build();

        when(jwtService.getAuthenticatedUserId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserService userService = new UserService(userRepository, null, null, jwtService);

        // Act
        UserLoadDTO result = userService.loadUser();

        // Assert
        assertEquals(userId, result.userId());
        assertEquals(user.getUsername(), result.username());

        verify(jwtService).getAuthenticatedUserId();
        verify(userRepository).findById(userId);
    }

    @Test
    void testSearchUser() throws Exception {
        // Create some test users
        User user1 = User.builder()
                .username("JohnD")
                .firstName("John")
                .lastName("Doe")
                .email("John.Doe@gmail.com")
                .password("password")
                .build();
        User user2 = User.builder()
                .username("JaneD")
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane.Doe@gmail.com")
                .password("password")
                .build();
        User user3 = User.builder()
                .username("JorgeD")
                .firstName("Jorge")
                .lastName("Doe")
                .email("Jorge.Doe@gmail.com")
                .password("password")
                .build();   ;
        userRepository.saveAll(List.of(user1, user2, user3));

        // Test searching for a user that exists
        String result = userService.searchUser("j");
        List<UserSearchDTO> users = UserMapper.userSearchDTO(userRepository.findByUsernameContaining("j"));
        String expected = new ObjectMapper().writeValueAsString(users);
        assertEquals(expected, result);

        // Test searching for a user that doesn't exist
        result = userService.searchUser("xyz");
        expected = "[]";
        assertEquals(expected, result);
    }

    @Test
    public void testIsSuperUser() {
        // Create a user
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setPassword("password");

        // Create a fridge and add the user as a member
        Fridge fridge = new Fridge();
        fridge.setFridgeId(1L);
        fridge.setFridgeName("Test Fridge");
        FridgeMember membership = new FridgeMember(new FridgeMemberId(user.getUserId(), fridge.getFridgeId()),user, fridge, false);
        membership.setSuperUser(true);
        fridge.getMembers().add(membership);
        user.getMemberships().add(membership);

        // Mock the user repository to return the test user
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Test that the user is a superuser of the fridge
        boolean isSuperUser = userService.isSuperUser(1L, "testuser");
        assertTrue(isSuperUser);

        // Test that the user is not a superuser of another fridge
        assertThrows(UnauthorizedException.class, () -> userService.isSuperUser(2L, "testuser"));

        // Test that an exception is thrown if the user does not belong to the fridge
        assertThrows(UnauthorizedException.class, () -> userService.isSuperUser(3L, "testuser"));
    }



}