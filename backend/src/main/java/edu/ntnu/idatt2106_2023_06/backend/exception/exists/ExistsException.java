package edu.ntnu.idatt2106_2023_06.backend.exception.exists;

public class ExistsException extends RuntimeException{

    public ExistsException(String attribute, String value) {
        super(String.format("%s with id %s already exists", attribute, value));
    }

    public ExistsException(String attribute, Long value) {
        super(String.format("%s with id %d already exists", attribute, value));
    }

    public ExistsException(String type, String attribute, String value) {
        super(String.format("%s with %s: %s, already exists", type, attribute, value));
    }

}
