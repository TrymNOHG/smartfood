package edu.ntnu.idatt2106_2023_06.backend.exception.exists;

public class UserExistsException extends ExistsException{

    public UserExistsException(String username) {
        super("User", username);
    }

}
