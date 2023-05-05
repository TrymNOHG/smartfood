package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 * This exception is thrown when an allergen cannot be found.
 */
public class AllergenNotFoundException extends NotFoundException{

    /**
     * This method constructs a new AllergenNotFoundException with a given attribute and value.
     * @param attribute A specific attribute of the allergen that was not found.
     * @param value     The value of the allergen not found
     */
    public AllergenNotFoundException(String attribute, String value) {
        super(attribute, value);
    }
}
