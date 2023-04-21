package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 *  Exception thrown when an entity with a certain attribute value or ID cannot be found in the database.
 *
 *  @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public class NotFoundException extends RuntimeException {

    /**
     * This method constructs a new NotFoundException with the given attribute name and value.
     * @param attribute The name of the attribute that was searched for, given as a String
     * @param value     The value of the attribute that was searched for, given as a String
     */
    public NotFoundException(String attribute, String value) {
        super(String.format("%s %s does not exist", attribute, value));
    }

    /**
     * This method constructs a new NotFoundException with the given attribute name and ID.
     * @param attribute The name of the attribute that was searched for, given as a String
     * @param value     The ID of the entity that was searched for, given as a Long value
     */
    public NotFoundException(String attribute, Long value) {
        super(String.format("%s with id %d does not exist", attribute, value));
    }

}
