package com.jimtang.saver.filters;

import java.util.function.Predicate;

/**
 * Created by tangz on 10/4/2015.
 */
class ContainsTextPredicate implements Predicate<String> {

    private String testString;

    ContainsTextPredicate(String testString) {
        this.testString = testString;
    }

    @Override
    public boolean test(String fileName) {
        return fileName.contains(testString);
    }
}
