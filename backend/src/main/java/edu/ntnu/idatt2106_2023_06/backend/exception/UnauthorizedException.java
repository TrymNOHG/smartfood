package edu.ntnu.idatt2106_2023_06.backend.exception;

/**
 *  An exception thrown when a user is not authorized to perform a certain action.
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * This method constructs an UnauthorizedException with a default error message indicating the username
     * that is not authorized.
     * @param username the username of the user that is not authorized.
     */
    public UnauthorizedException(String username) {
        super(String.format("User, %s, is not authorized", username));
    }

    /**
     * This method constructs an UnauthorizedException with a custom error message and indicating the
     * username that is not authorized.
     *
     * @param username the username of the user that is not authorized.
     * @param message the custom error message.
     */
    public UnauthorizedException(String username, String message) {
        super(String.format("User, %s, is not authorized", username) + message);
    }

    /**
     * This method constructs an UnauthorizedException with a default error message indicating that the user
     * is not authorized.
     */
    public UnauthorizedException() {
        super("User is not authorized");
    }

}
