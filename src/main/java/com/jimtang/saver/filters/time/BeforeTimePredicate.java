package com.jimtang.saver.filters.time;

import java.time.LocalDateTime;

/**
 * Created by tangz on 10/4/2015.
 */
public class BeforeTimePredicate implements TimePredicate {

    private LocalDateTime dateTimeUpper;

    BeforeTimePredicate(LocalDateTime dateTimeUpper) {
        this.dateTimeUpper = dateTimeUpper;
    }

    @Override
    public boolean test(LocalDateTime dateTime) {
        return dateTime.compareTo(dateTimeUpper) < 0;
    }
}
