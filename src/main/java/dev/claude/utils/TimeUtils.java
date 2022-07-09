package dev.claude.utils;


import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    private static final String[] StartCorrespondences =
            {"08:00", "09:00", "10:00", "11:00", "13:30", "14:30", "15:30", "16:30"};
    private static final String[] EndCorrespondences =
            {"09:00", "10:00", "11:00", "12:00", "14:30", "15:30", "16:30", "17:30"};
    /**
     * this function returns the day of the week of a date as a number, from monday (1) to sunday (7)
     * @param date the date to get the day of the week
     * @return the day of the week (1 to 7)
     */
    public int intGetDayOfTheWeek(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }
    public int getDayOfTheWeekFromPeriodOfTheWeek(Integer periodOfTheWeek) {
        return periodOfTheWeek/7 + 1;
    }
    public int getPeriodOfTheDayFromPeriodOfTheWeek(Integer periodOfTheWeek) {
        return periodOfTheWeek % 7 - getDayOfTheWeekFromPeriodOfTheWeek(periodOfTheWeek);
    }
    public java.sql.Date getStartFromPeriod (int periodOfTheWeek, LocalDate localDate) {
        return getDateFromPeriod(periodOfTheWeek, localDate, StartCorrespondences);
    }
    public java.sql.Date getEndFromPeriod (int periodOfTheWeek, LocalDate localDate) {
        return getDateFromPeriod(periodOfTheWeek, localDate, EndCorrespondences);
    }
    @NotNull
    private java.sql.Date getDateFromPeriod(int periodOfTheWeek, LocalDate localDate, String[] endCorrespondences) {
        String[] endHourString = endCorrespondences[getPeriodOfTheDayFromPeriodOfTheWeek(periodOfTheWeek)].split(":");
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        Date date = Date.from(instant);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR, Integer.parseInt(endHourString[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(endHourString[1]));
        return new java.sql.Date(calendar.getTimeInMillis());
    }
}
