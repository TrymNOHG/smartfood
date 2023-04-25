package edu.ntnu.idatt2106_2023_06.backend.exception.illegal;

public class IllegalStatValueException extends IllegalException {

    public IllegalStatValueException(int lowerBounds, int upperBounds) {
        super(String.format("Stat value must be between %d and %d", lowerBounds, upperBounds));
    }

    public IllegalStatValueException(int lowerBounds) {
        super(String.format("Stat value must be greater than %d", lowerBounds));
    }

    public IllegalStatValueException() {
        super("Stat value must be positive");
    }

}
