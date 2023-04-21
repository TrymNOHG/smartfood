package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 *  This exception is thrown when a file with a given attribute and value cannot be found.
 *
 * @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public class FileNotFoundException extends NotFoundException {

    /**
     * This method constructs a new FileNotFoundException with the given attribute and value.
     *
     * @param attribute The attribute of the file that was not found, given as a String
     * @param value     The value of the attribute that was not found, given as a String
     */
    public FileNotFoundException(String attribute, String value) {
        super(attribute, value);
    }

    /**
     * This method constructs a new FileNotFoundException with the given attribute and value.
     *
     * @param attribute The attribute of the file that was not found, given as a String
     * @param value     The value of the attribute that was not found, given as a Long object.
     */
    public FileNotFoundException(String attribute, Long value) {
        super(attribute, value);
    }
}
