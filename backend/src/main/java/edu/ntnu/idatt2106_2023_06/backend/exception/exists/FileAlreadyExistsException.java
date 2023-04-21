package edu.ntnu.idatt2106_2023_06.backend.exception.exists;

/**
 *  This is an exception thrown when trying to create a file that already exists.
 *
 *  @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public class FileAlreadyExistsException extends ExistsException{

    /**
     * This method constructs a new FileAlreadyExistsException with the specified attribute and value.
     *
     * @param attribute The attribute of the file that already exists, given as a String
     * @param value     The value of the attribute of the file that already exists, given as a String
     */
    public FileAlreadyExistsException(String attribute, String value) {
        super(attribute, value);
    }

    /**
     * This method constructs a new FileAlreadyExistsException with the specified attribute and value.
     *
     * @param attribute The attribute of the file that already exists, given as a String
     * @param value     The value of the attribute of the file that already exists, given as a Long object
     */

    public FileAlreadyExistsException(String attribute, Long value) {
        super(attribute, value);
    }
}
