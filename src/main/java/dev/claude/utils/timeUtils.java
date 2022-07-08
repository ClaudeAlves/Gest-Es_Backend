package dev.claude.utils;

import java.sql.Date;
import java.util.Calendar;

public class timeUtils {
    /**
     * this function returns the day of the week of a date as a number, from sunday (1) to saturday (7)
     * @param date the date to get the day of the week
     * @return the day of the week (1 to 7)
     */
    public int intGetDayOfTheWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}
