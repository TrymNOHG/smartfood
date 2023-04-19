package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class FileNotFoundException extends NotFoundException {
    public FileNotFoundException(String attribute, String value) {
        super(attribute, value);
    }

    public FileNotFoundException(String attribute, Long value) {
        super(attribute, value);
    }
}
