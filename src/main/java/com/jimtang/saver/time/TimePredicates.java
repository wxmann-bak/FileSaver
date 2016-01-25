package com.jimtang.saver.time;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Created by tangz on 1/24/2016.
 */
public final class TimePredicates {

    private TimePredicates() {
    }

    public static Predicate<LocalDateTime> before(LocalDateTime time) {
        return dateTimeToTest -> dateTimeToTest.isBefore(time);
    }

    public static Predicate<LocalDateTime> after(LocalDateTime time) {
        return dateTimeToTest -> dateTimeToTest.isAfter(time);
    }

    public static Predicate<LocalDateTime> between(LocalDateTime initialTime, LocalDateTime finalTime) {
        return after(initialTime).and(before(finalTime));
    }
}
