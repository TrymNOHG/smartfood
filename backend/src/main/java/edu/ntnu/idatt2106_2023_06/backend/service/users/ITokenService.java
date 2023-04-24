package edu.ntnu.idatt2106_2023_06.backend.service.users;

import edu.ntnu.idatt2106_2023_06.backend.model.users.User;

public interface ITokenService {

    void saveToken(String tokenValue, User user);

}
