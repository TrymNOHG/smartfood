package edu.ntnu.idatt2106_2023_06.backend.utils;

import java.util.HashMap;
import java.util.Map;


/**
 * This enumeration contains all the important unit types that items may have. It also contains a way to check if a
 * string represents an accepted type or not. A hashmap is used in order for efficient look up of the different unit types.
 *
 * @author Trym Hamer Gudvangen
 */
public enum UnitType {
    PIECES("pieces", "pk", "stk"),
    GRAMS("g", "gram", "grams"),
    KILOGRAMS("kg", "kilogram", "kilograms"),
    MILLILITERS("ml", "milliliter", "milliliters"),
    DECILITERS("dl", "deciliter", "deciliters"),
    GALLONS("gal", "gallon", "gallons"),
    LITERS("l", "liter", "liters"),
    POUNDS("lb", "pound", "pounds"),
    OUNCES("oz", "ounce", "ounces");

    private static final Map<String, UnitType> stringToEnumMap = new HashMap<>();
    static {
        for (UnitType unitType : values()) {
            for (String stringValue : unitType.stringValues) {
                stringToEnumMap.put(stringValue, unitType);
            }
        }
    }

    private final String[] stringValues;

    UnitType(String... stringValues) {
        this.stringValues = stringValues;
    }

    public static boolean contains(String s) {
        return stringToEnumMap.containsKey(s.toLowerCase());
    }

    public static UnitType fromString(String s) {
        return stringToEnumMap.get(s.toLowerCase());
    }

    public static String getDisplayName(UnitType unitType) {
        return unitType.stringValues[0];
    }
}
