package edu.ntnu.idatt2106_2023_06.backend.service.users;

import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.TokenNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.TokenRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    @Override
    public void saveToken(String tokenValue, Long userId) {
        logger.info("Checking validity of token value");
        Objects.requireNonNull(tokenValue, "Token Value");
        logger.info("Token is not null.");

        logger.info("Checking if user exists");
        User user = userRepository.findById(userId).
                orElseThrow();
        //TODO: add throw condition and finish method

    }

}
