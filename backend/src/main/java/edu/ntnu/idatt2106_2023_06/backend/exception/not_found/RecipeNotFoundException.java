package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 * This exception is thrown when a Recipe is not found.
 */
public class RecipeNotFoundException extends NotFoundException{

    /**
     * This method constructs a new RecipeNotFoundException with a given attribute and value.
     * @param attribute The specific attribute of the recipe not found.
     * @param value     The value of the recipe not found.
     */
    public RecipeNotFoundException(String attribute, String value) {
        super(attribute, value);
    }

    /**
     * This method constructs a new RecipeNotFoundException with a given attribute and value.
     * @param attribute The specific attribute of the recipe not found.
     * @param value     The id of the value not found.
     */
    public RecipeNotFoundException(String attribute, Long value) {
        super(attribute, value);
    }
}
