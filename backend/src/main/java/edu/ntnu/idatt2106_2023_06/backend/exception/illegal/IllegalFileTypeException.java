package edu.ntnu.idatt2106_2023_06.backend.exception.illegal;

public class IllegalFileTypeException extends IllegalException {

    public IllegalFileTypeException(String fileType) {
        super("File type " + fileType + " is not valid");
    }
}
