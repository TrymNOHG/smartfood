package edu.ntnu.idatt2106_2023_06.backend.service.users;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import edu.ntnu.idatt2106_2023_06.backend.exception.InvalidTokenException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.TokenNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.users.Token;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.TokenRepository;

public class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveToken() {
        User user = new User();
        user.setUserId(1L);
        String tokenValue = "token";
        LocalDateTime timeCreated = LocalDateTime.now();
        LocalDateTime timeExpires = timeCreated.plusHours(24);
        Token token = Token.builder()
                .token(tokenValue)
                .timeCreated(timeCreated)
                .timeExpires(timeExpires)
                .user(user)
                .build();

        tokenService.saveToken(tokenValue, user);

        verify(tokenRepository).save(argThat(savedToken ->
                savedToken.getToken().equals(tokenValue) &&
                        savedToken.getTimeConfirmed() == null
        ));
    }

    @Test
    public void testConfirmToken() {
        String tokenValue = "token";
        Token token = Token.builder()
                .token(tokenValue)
                .user(new User())
                .timeCreated(LocalDateTime.now())
                .timeExpires(LocalDateTime.now().plusHours(24))
                .build();
        when(tokenRepository.findTokenByToken(tokenValue)).thenReturn(Optional.of(token));

        tokenService.confirmToken(tokenValue);

        verify(tokenRepository).save(any(Token.class));
    }

    @Test
    public void testConfirmTokenTokenNotFound() {
        String tokenValue = "token";
        when(tokenRepository.findTokenByToken(tokenValue)).thenReturn(Optional.empty());

        try {
            tokenService.confirmToken(tokenValue);
        } catch (TokenNotFoundException e) {
            // Expected exception
        }

    }

    @Test
    public void testConfirmTokenInvalidToken() {
        String tokenValue = "token";
        Token token = Token.builder()
                .token(tokenValue)
                .user(new User())
                .timeCreated(LocalDateTime.now().minusHours(48))
                .timeExpires(LocalDateTime.now().minusHours(24))
                .build();
        when(tokenRepository.findTokenByToken(tokenValue)).thenReturn(Optional.of(token));

        try {
            tokenService.confirmToken(tokenValue);
        } catch (InvalidTokenException e) {
            // Expected exception
        }

    }
}
