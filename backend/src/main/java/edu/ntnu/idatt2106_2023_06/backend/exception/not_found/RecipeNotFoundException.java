package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class RecipeNotFoundException extends NotFoundException{

    public RecipeNotFoundException(String attribute, String value) {
        super(attribute, value);
    }

    public RecipeNotFoundException(String attribute, Long value) {
        super(attribute, value);
    }
}
