package dev.claude.utils;


import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.*;
@Component
public class TimeUtils {
    private static final String[] StartCorrespondences =
            {"08:00", "09:00", "10:00", "11:00", "13:30", "14:30", "15:30", "16:30"};
    private static final String[] EndCorrespondences =
            {"09:00", "10:00", "11:00", "12:00", "14:30", "15:30", "16:30", "17:30"};
    public Integer getDayOfTheWeekFromPeriodOfTheWeek(Integer periodOfTheWeek) {
        return ((periodOfTheWeek - 1) / 8);
    }
    public Integer getPeriodOfTheDayFromPeriodOfTheWeek(Integer periodOfTheWeek) {
        return ((periodOfTheWeek - 1) % 8);
    }
    public LocalDateTime getStartFromPeriod (int periodOfTheWeek, LocalDate localDate) {
        return getDateFromPeriod(periodOfTheWeek, localDate, StartCorrespondences);
    }
    public LocalDateTime getEndFromPeriod (int periodOfTheWeek, LocalDate localDate) {
        return getDateFromPeriod(periodOfTheWeek, localDate, EndCorrespondences);
    }
    @NotNull
    private LocalDateTime getDateFromPeriod(int periodOfTheWeek, LocalDate localDate, String[] correspondences) {
        String[] hourString = correspondences[getPeriodOfTheDayFromPeriodOfTheWeek(periodOfTheWeek)].split(":");
        return localDate.atTime(Integer.parseInt(hourString[0]), Integer.parseInt(hourString[1]));
    }
}
