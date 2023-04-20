package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 * An exception that indicates that a user was not found. It extends the NotFoundException class,
 * which extends RuntimeException, so it's a RuntimeException.
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public class UserNotFoundException extends NotFoundException{

    /**
     * This method constructs a UserNotFoundException with the specified value as the cause.
     * @param value The value that caused the exception to be thrown, given as a String.
     */
    public UserNotFoundException(String value) {
        super("User", value);
    }

    /**
     * This method constructs a UserNotFoundException with the specified id as the cause.
     * @param id The id that caused the exception to be thrown, given as a Long.
     */
    public UserNotFoundException(Long id) {
        super("User", id);
    }
}
