package com.jimtang.saver.filters.time;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * Created by tangz on 10/4/2015.
 */
public final class TimePredicates {

    public static TimePredicate after(TimeExtractor converter, DateTimeFormatter formatter, String timeStr) {
        LocalDateTime dateTime = LocalDateTime.parse(timeStr, formatter);
        return new AfterTimePredicate(dateTime) {
            @Override
            protected LocalDateTime extractTimeFromFile(String fileName) {
                return converter.extractDateTime(fileName);
            }
        };
    }

    public static TimePredicate onDayBetweenHours(TimeExtractor converter,
                                                  int yr,
                                                  int mo,
                                                  int day,
                                                  int hrLower,
                                                  int hrUpper)
    {
        LocalDateTime dateTimeLower = LocalDateTime.of(yr, mo, day, hrLower, 0);
        LocalDateTime dateTimeUpper = LocalDateTime.of(yr, mo, day, hrUpper, 0);
        return new BetweenTimePredicate(dateTimeLower, dateTimeUpper) {
            @Override
            protected LocalDateTime extractTimeFromFile(String fileName) {
                return converter.extractDateTime(fileName);
            }
        };
    }

    public static TimePredicate betweenDaysWithHours(TimeExtractor converter,
                                                     DateTimeFormatter dateTimeFormatter,
                                                     String dateTimeLowerStr,
                                                     String dateTimeUpperStr)
    {
        LocalDateTime dateTimeLower = LocalDateTime.parse(dateTimeLowerStr, dateTimeFormatter);
        LocalDateTime dateTimeUpper = LocalDateTime.parse(dateTimeUpperStr, dateTimeFormatter);
        return new BetweenTimePredicate(dateTimeLower, dateTimeUpper) {
            @Override
            protected LocalDateTime extractTimeFromFile(String fileName) {
                return converter.extractDateTime(fileName);
            }
        };
    }

    public static TimePredicate betweenDaysWithHours(TimeExtractor converter,
                                                     int yr1,
                                                     int mo1,
                                                     int day1,
                                                     int hr1,
                                                     int yr2,
                                                     int mo2,
                                                     int day2,
                                                     int hr2)
    {
        LocalDateTime dateTimeLower = LocalDateTime.of(yr1, mo1, day1, hr1, 0);
        LocalDateTime dateTimeUpper = LocalDateTime.of(yr2, mo2, day2, hr2, 0);
        return new BetweenTimePredicate(dateTimeLower, dateTimeUpper) {
            @Override
            protected LocalDateTime extractTimeFromFile(String fileName) {
                return converter.extractDateTime(fileName);
            }
        };
    }

    public static TimePredicate betweenDaysWithHours(TimeExtractor converter,
                                                     int yr,
                                                     int mo,
                                                     int day1,
                                                     int hr1,
                                                     int day2,
                                                     int hr2)
    {
        LocalDateTime dateTimeLower = LocalDateTime.of(yr, mo, day1, hr1, 0);
        LocalDateTime dateTimeUpper = LocalDateTime.of(yr, mo, day2, hr2, 0);
        return new BetweenTimePredicate(dateTimeLower, dateTimeUpper) {
            @Override
            protected LocalDateTime extractTimeFromFile(String fileName) {
                return converter.extractDateTime(fileName);
            }
        };
    }
}
