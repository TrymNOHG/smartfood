package edu.ntnu.idatt2106_2023_06.backend.model;

import edu.ntnu.idatt2106_2023_06.backend.model.recipe.Day;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DayTest {

    public static DayOfWeek getDay(Day day) {
        return switch(day) {
            case MONDAY -> DayOfWeek.MONDAY;
            case TUESDAY -> DayOfWeek.TUESDAY;
            case WEDNESDAY -> DayOfWeek.WEDNESDAY;
            case THURSDAY -> DayOfWeek.THURSDAY;
            case FRIDAY -> DayOfWeek.FRIDAY;
            case SATURDAY -> DayOfWeek.SATURDAY;
            case SUNDAY -> DayOfWeek.SUNDAY;
        };
    }

    @ParameterizedTest(name = "{index} - Checking next {0}")
    @EnumSource(value = Day.class)
    public void testGetNextDateTime(Day day) {
        DayOfWeek dow = getDay(day);
        LocalDateTime now = LocalDateTime.now();
        int daysUntilNextDow = (dow.getValue() + 7 - now.getDayOfWeek().getValue()) % 7;
        DayOfWeek expectedDow = LocalDateTime.now().plusDays(daysUntilNextDow).getDayOfWeek();

        Assertions.assertEquals(expectedDow, day.getNextDateTime().getDayOfWeek());
    }

}
