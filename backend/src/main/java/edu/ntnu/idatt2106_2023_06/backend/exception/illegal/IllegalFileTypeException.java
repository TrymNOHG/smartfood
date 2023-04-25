package edu.ntnu.idatt2106_2023_06.backend.exception.illegal;

/**
 *  This exception is thrown when a file with an illegal file type is uploaded.
 *
 *  @author Brage Halvorsen Kvamme
 */
public class IllegalFileTypeException extends IllegalException {

    /**
     * This method constructs a new IllegalFileTypeException with the specified file type.
     *
     * @param fileType The file type that is not valid, given as a String
     */
    public IllegalFileTypeException(String fileType) {
        super("File type " + fileType + " is not valid");
    }
}
