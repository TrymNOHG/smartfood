package edu.ntnu.idatt2106_2023_06.backend.exception.illegal;

/**
 *  This exception is thrown when an illegal argument is passed to a method.
 *
 *  @author Brage Halvorsen Kvamme
 */
public class IllegalException extends RuntimeException {

    /**
     * This method constructs a new IllegalException with the specified message.
     *
     * @param message The message of the exception, given as a String
     */
    public IllegalException(String message) {
        super(message);
    }
}
