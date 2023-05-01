package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class AllergenNotFoundException extends NotFoundException{

    public AllergenNotFoundException(String attribute, String value) {
        super(attribute, value);
    }
}
