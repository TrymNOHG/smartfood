package edu.ntnu.idatt2106_2023_06.backend.service.users;

public interface ITokenService {

    void saveToken(String tokenValue, Long userId);

}
