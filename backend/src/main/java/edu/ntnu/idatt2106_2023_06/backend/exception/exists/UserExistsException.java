package edu.ntnu.idatt2106_2023_06.backend.exception.exists;

/**
 *  Exception thrown when attempting to create a new user with a username that already exists in the system.
 *
 *  @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public class UserExistsException extends ExistsException{

    /**
     * This method constructs a new UserExistsException with the provided username.
     *
     * @param username The username that already exists, given as a String
     */
    public UserExistsException(String username) {
        super("User", username);
    }

    /**
     * This method constructs a new UserExistsException with the provided attribute and value.
     *
     * @param attribute The attribute of the user that already exists, given as a String
     * @param value     The value of the attribute that already exists, given as a String
     */
    public UserExistsException(String attribute, String value) {
        super("User", attribute, value);
    }

}
