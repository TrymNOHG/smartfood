package edu.ntnu.idatt2106_2023_06.backend.exception.exists;

public class FileAlreadyExistsException extends ExistsException{

    public FileAlreadyExistsException(String attribute, String value) {
        super(attribute, value);
    }

    public FileAlreadyExistsException(String attribute, Long value) {
        super(attribute, value);
    }
}
