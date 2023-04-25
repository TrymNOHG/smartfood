package edu.ntnu.idatt2106_2023_06.backend.exception.illegal;

public class IllegalStatTypeException extends IllegalException {
    public IllegalStatTypeException(int statType) {
        super(String.format("Stat type %d is not valid", statType));
    }
}
