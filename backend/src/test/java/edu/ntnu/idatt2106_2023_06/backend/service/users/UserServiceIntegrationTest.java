package edu.ntnu.idatt2106_2023_06.backend.service.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertTrue;

import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.Fridge;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testLoadUserDTOByUsername() {
        User user = User
                .builder()
                .userId(1L)
                .username("OleN")
                .password("password")
                .firstName("Ole")
                .lastName("Norman")
                .email("test@gamil.com")
                .build();

        userRepository.save(user);

        // When
        UserLoadDTO userLoadDTO = userService.loadUserDTOByUsername(user.getUsername());

        // Then
        assertThat(userLoadDTO.username()).isEqualTo(user.getUsername());
    }

    @Test
    public void testLoadUserDTOByUsernameNotFound() {
        // Given
        String username = "nonexistentuser";

        // When / Then
        assertThatThrownBy(() -> userService.loadUserDTOByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(username);
    }

}