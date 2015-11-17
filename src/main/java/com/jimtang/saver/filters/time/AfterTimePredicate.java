package com.jimtang.saver.filters.time;

import java.time.LocalDateTime;

/**
 * Created by tangz on 10/4/2015.
 */
public class AfterTimePredicate implements TimePredicate {

    private LocalDateTime dateTimeLower;

    AfterTimePredicate(LocalDateTime dateTimeLower) {
        this.dateTimeLower = dateTimeLower;
    }

    @Override
    public boolean test(LocalDateTime dateTime) {
        return dateTime.compareTo(dateTimeLower) > 0;
    }
}
