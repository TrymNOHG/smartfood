package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String attribute, String value) {
        super(String.format("%s %s does not exist", attribute, value));
    }

    public NotFoundException(String attribute, Long value) {
        super(String.format("%s with id %d does not exist", attribute, value));
    }

}
