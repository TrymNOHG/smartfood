package edu.ntnu.idatt2106_2023_06.backend.model.recipe;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum Day {
    MONDAY(DayOfWeek.MONDAY),
    TUESDAY(DayOfWeek.TUESDAY),
    WEDNESDAY(DayOfWeek.WEDNESDAY),
    THURSDAY(DayOfWeek.THURSDAY),
    FRIDAY(DayOfWeek.FRIDAY),
    SATURDAY(DayOfWeek.SATURDAY),
    SUNDAY(DayOfWeek.SUNDAY);

    private final DayOfWeek dayOfWeek;

    Day(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalDateTime getNextDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek nowDow = now.getDayOfWeek();

        if (nowDow == dayOfWeek) {
            return now;
        } else {
            int daysUntilNextDow = (dayOfWeek.getValue() + 7 - nowDow.getValue()) % 7;
            return now.plusDays(daysUntilNextDow);
        }
    }
}