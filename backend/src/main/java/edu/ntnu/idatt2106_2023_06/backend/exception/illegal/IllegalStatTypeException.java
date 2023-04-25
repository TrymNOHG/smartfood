package edu.ntnu.idatt2106_2023_06.backend.exception.illegal;

/**
 *  This exception is thrown when a stat type is not valid.
 *
 *  @author Brage Halvorsen Kvamme
 */
public class IllegalStatTypeException extends IllegalException {

    /**
     * This method constructs a new IllegalStatTypeException with the specified stat type.
     *
     * @param statType The stat type that is not valid, given as an int
     */
    public IllegalStatTypeException(int statType) {
        super(String.format("Stat type %d is not valid", statType));
    }
}
