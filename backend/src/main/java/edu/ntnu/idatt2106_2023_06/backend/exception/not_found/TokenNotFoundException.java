package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 *  An exception to be thrown when a token is not found.
 *
 * @author Trym Hamer Gudvangen
 */
public class TokenNotFoundException extends NotFoundException{

    /**
     * This method constructs a new  with the specified token value.
     * @param token The token value, given as a String
     */
    public TokenNotFoundException(String token) {
        super("Token", token);
    }

    /**
     * This method constructs a new TokenNotFoundException with the specified user id.
     * @param tokenId The id of the token that was not found, given as a Long object.
     */
    public TokenNotFoundException(Long tokenId) {
        super("Token", tokenId);
    }

}
