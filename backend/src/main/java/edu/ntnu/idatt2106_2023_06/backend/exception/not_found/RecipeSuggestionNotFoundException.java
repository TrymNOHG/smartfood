package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class RecipeSuggestionNotFoundException extends NotFoundException{
    public RecipeSuggestionNotFoundException(String value) {
        super("RecipeSuggestion", value);
    }

    public RecipeSuggestionNotFoundException(Long value) {
        super("RecipeSuggestion", value);
    }
}
