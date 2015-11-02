package com.jimtang.saver.filters.time;

import java.time.LocalDateTime;

/**
 * Created by tangz on 10/4/2015.
 */
public abstract class AfterTimePredicate extends TimePredicate {

    private LocalDateTime dateTimeLower;

    public AfterTimePredicate(LocalDateTime dateTimeLower) {
        this.dateTimeLower = dateTimeLower;
    }

    @Override
    protected boolean pass(LocalDateTime dateTime) {
        return dateTime.compareTo(dateTimeLower) > 0;
    }
}
