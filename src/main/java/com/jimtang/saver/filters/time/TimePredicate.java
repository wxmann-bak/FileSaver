package com.jimtang.saver.filters.time;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Created by tangz on 10/4/2015.
 */
public abstract class TimePredicate implements Predicate<String> {

    protected abstract LocalDateTime extractTimeFromFile(String fileName);

    protected abstract boolean pass(LocalDateTime dateTime);

    @Override
    public boolean test(String fileName) {
        return pass(extractTimeFromFile(fileName));
    }
}
