package edu.ntnu.idatt2106_2023_06.backend.service.security;

import edu.ntnu.idatt2106_2023_06.backend.dto.security.AuthenticationRequestDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.security.AuthenticationResponseDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserCreateDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.exists.UserExistsException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    /**
     * Registers a user to the system.
     *
     * @param userCreateDTO the information of the user to be registered.
     * @return an AuthenticationResponse containing the JWT token of the user.
     */
    @Transactional
    public AuthenticationResponseDTO register(UserCreateDTO userCreateDTO) {
        User user = User
                .builder()
                .username(userCreateDTO.username())
                .password(passwordEncoder.encode(userCreateDTO.password()))
                .firstName(userCreateDTO.firstName())
                .lastName(userCreateDTO.lastName())
                .email(userCreateDTO.email())
                .members(new HashSet<>())
                .build();
        if(userRepository.findByEmail(userCreateDTO.email()).isPresent())
            throw new UserExistsException("email", userCreateDTO.email());
        if (userRepository.findByUsername(userCreateDTO.username()).isPresent())
            throw new UserExistsException("username", userCreateDTO.username());

        userRepository.save(user);

        logger.info(String.format("User %s has been saved in the DB!", user.getUsername()));

        String jwtToken = jwtService.generateToken(user);
        logger.info("Their JWT is: " + jwtToken);

        return AuthenticationResponseDTO
                .builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates a user with the given credentials.
     *
     * @param request the authentication request containing the username and password of the user.
     * @return an AuthenticationResponse containing the JWT token of the authenticated user.
     * @throws UsernameNotFoundException if the username of the user is not found in the database.
     */
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new UserNotFoundException(request.email())
        );
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        request.password()
                )
        );

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO
                .builder()
                .token(jwtToken)
                .build();
    }
}
