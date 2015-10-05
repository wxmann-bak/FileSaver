package com.jimtang.saver.filters.time;

import java.time.LocalDateTime;

/**
 * Created by tangz on 10/4/2015.
 */
public abstract class BetweenTimePredicate extends TimePredicate {

    private LocalDateTime dateTimeLower;
    private LocalDateTime dateTimeUpper;

    public BetweenTimePredicate(LocalDateTime dateTimeLower, LocalDateTime dateTimeUpper) {
        if (dateTimeUpper.compareTo(dateTimeLower) < 0) {
            throw new IllegalArgumentException("Upper bound of date-time is greater than lower bound");
        }
        this.dateTimeLower = dateTimeLower;
        this.dateTimeUpper = dateTimeUpper;
    }

    @Override
    protected boolean pass(LocalDateTime dateTime) {
        return dateTime.compareTo(dateTimeLower) > 0 && dateTime.compareTo(dateTimeUpper) < 0;
    }
}
