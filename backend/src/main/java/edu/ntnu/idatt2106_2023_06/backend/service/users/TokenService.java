package edu.ntnu.idatt2106_2023_06.backend.service.users;

import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.TokenNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.users.Token;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.TokenRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class allows for emails to be sent to users regarding issues such as registration and password resetting.
 *
 * @author Trym Hamer Gudvangen
 */
@Service
@AllArgsConstructor
public class TokenService implements ITokenService{

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Transactional
    @Override
    public void saveToken(String tokenValue, User user) {
        logger.info("Checking validity of token value");
        Objects.requireNonNull(tokenValue, "Token Value");
        logger.info("Token is not null.");

        Objects.requireNonNull(user, "User");


        logger.info("User id : " + user.getUserId());
        Token token = Token
                .builder()
                .token(tokenValue)
                .timeCreated(LocalDateTime.now())
                .timeExpires(LocalDateTime.now().plusHours(24))
                .user(user)
                .build();

        tokenRepository.save(token);
        logger.info("Token has been saved for " + user.getUsername());
    }

}
