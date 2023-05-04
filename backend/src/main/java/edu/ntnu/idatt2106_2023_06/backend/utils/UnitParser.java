package edu.ntnu.idatt2106_2023_06.backend.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class contains methods to extract the amount and unit of an item from its product name. It does so using a complex
 * regex, as well as through a lot of parsing. Furthermore, it uses the {@link UnitType} class in order to handle
 * exceptions that the regex allows to pass through.
 *
 * @author Trym Hamer Gudvangen
 */
public class UnitParser {

    private static final Pattern PATTERN = Pattern.compile("(\\d+[.,]?\\d*(?:\\s*[x*/]\\s*\\d+[.,]?\\d*)?)\\s*(\\p{L}+)?");
    public static Object[] parse(String productName) {
        double amount = 0;
        UnitType unit = UnitType.PIECES;
        boolean hasValue = false;

        Matcher matcher = PATTERN.matcher(productName);
        while (matcher.find()) {
            String match = matcher.group(1).replace(",", ".");
            String matchedUnit = matcher.group(2);
            if (matchedUnit != null && !matchedUnit.isBlank()) {
                matchedUnit = matchedUnit.toLowerCase().trim();


                unit = !UnitType.contains(matchedUnit) ? UnitType.PIECES : UnitType.fromString(matchedUnit);

                switch (unit) {
                    case KILOGRAMS -> {
                        amount = parseAmount(match) * 1000.0;
                        unit = UnitType.GRAMS;
                    }
                    case LITERS -> {
                        amount = parseAmount(match) * 1000.0;
                        unit = UnitType.MILLILITERS;
                    }
                    case DECILITERS -> {
                        amount = parseAmount(match) * 100.0;
                        unit = UnitType.MILLILITERS;
                    }
                    case GALLONS -> unit = UnitType.LITERS;
                    case POUNDS -> {
                        amount = (parseAmount(match) / 2.20462) * 1000;
                        unit = UnitType.GRAMS;
                    }
                    case PIECES -> {
                        if(!hasValue) {
                            amount = parseAmount(match);
                        }
                    }
                    default -> amount = parseAmount(match);
                }
            } else {
                amount = 1;
                unit = UnitType.PIECES;
            }

            hasValue = true;
        }

        if (amount == 0) {
            amount = 1;
            unit = UnitType.PIECES;
        }

        return new Object[]{amount, UnitType.getDisplayName(unit)};
    }

    private static double parseAmount(String amountStr) {
        double amount;

        if (amountStr.contains("/")) {
            String[] parts = amountStr.split("/");
            amount = Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
        } else if (amountStr.contains("x")) {
            String[] parts = amountStr.split("x");
            amount = Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]);
        }
        else {
            amount = Double.parseDouble(amountStr);
        }

        return amount;
    }


}
