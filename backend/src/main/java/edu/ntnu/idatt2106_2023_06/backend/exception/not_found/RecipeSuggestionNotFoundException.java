package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 * This exception is thrown when a recipe suggestion is not found
 */
public class RecipeSuggestionNotFoundException extends NotFoundException{

    /**
     * This method constructs a new RecipeSuggestionNotFoundException with the specified value.
     * @param value The value used to search for the recipe suggestion that was not found.
     */
    public RecipeSuggestionNotFoundException(String value) {
        super("RecipeSuggestion", value);
    }

    /**
     * This method constructs a new RecipeSuggestionNotFoundException with the specified value.
     * @param value The value used to search for the recipe suggestion that was not found.
     */
    public RecipeSuggestionNotFoundException(Long value) {
        super("RecipeSuggestion", value);
    }
}
