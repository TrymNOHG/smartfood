package edu.ntnu.idatt2106_2023_06.backend.exception;

/**
 *  This exception is thrown to indicate that a token is invalid.
 *
 * @author Trym Hamer Gudvangen
 */
public class InvalidTokenException extends IllegalArgumentException{

    /**
     * This method constructs an InvalidTokenException with no detail message.
     */
    public InvalidTokenException() {
    }

    /**
     * This method constructs an InvalidTokenException with the specified token as the detail message.
     * @param token The token that caused the exception.
     */
    public InvalidTokenException(String token) {
        super("Token " + token + " has expired");
    }

    /**
     * This method constructs an InvalidTokenException with the specified detail message and cause.
     * @param message The detail message.
     * @param cause The cause of the exception.
     */
    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * This method constructs an InvalidTokenException with the specified cause and a detail message of (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     * @param cause The cause of the exception.
     */
    public InvalidTokenException(Throwable cause) {
        super(cause);
    }
}
