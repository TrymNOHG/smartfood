package edu.ntnu.idatt2106_2023_06.backend.exception.illegal;

/**
 *  This exception is thrown when a stat value is not valid.
 *
 *  @author Brage Halvorsen Kvamme
 */
public class IllegalStatValueException extends IllegalException {

    /**
     * This method constructs a new IllegalStatValueException with the specified bounds for legal values.
     *
     * @param lowerBounds The lower bound of the stat value, given as an int
     * @param upperBounds The upper bound of the stat value, given as an int
     */
    public IllegalStatValueException(int lowerBounds, int upperBounds) {
        super(String.format("Stat value must be between %d and %d", lowerBounds, upperBounds));
    }

    /**
     * This method constructs a new IllegalStatValueException with the specified lower bound for legal values.
     *
     * @param lowerBounds The lower bound of the stat value, given as an int
     */
    public IllegalStatValueException(int lowerBounds) {
        super(String.format("Stat value must be greater than %d", lowerBounds));
    }

    /**
     * This method constructs a new IllegalStatValueException with the specified lower bound of zero.
     **/
    public IllegalStatValueException() {
        super("Stat value must be positive");
    }

}
