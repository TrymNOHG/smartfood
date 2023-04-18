package edu.ntnu.idatt2106_2023_06.backend.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String username) {
        super(String.format("User, %s, is not authorized", username));
    }


}
