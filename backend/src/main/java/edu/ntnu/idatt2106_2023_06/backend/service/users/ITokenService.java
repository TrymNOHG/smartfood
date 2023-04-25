package edu.ntnu.idatt2106_2023_06.backend.service.users;

import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import jakarta.transaction.Transactional;

public interface ITokenService {

    @Transactional
    void saveToken(String tokenValue, User user);


    @Transactional
    void confirmToken(String tokenValue);
}
