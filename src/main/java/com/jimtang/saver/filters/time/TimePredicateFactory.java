package com.jimtang.saver.filters.time;

import java.util.function.Predicate;

/**
 * Created by tangz on 11/16/2015.
 */
public interface TimePredicateFactory<T> {

    Predicate<T> after(String timeStr);

    Predicate<T> before(String timeStr);

    Predicate<T> between(String timeStr1, String timeStr2);
}
