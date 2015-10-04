package com.jimtang.saver.filters;

import java.util.function.Predicate;

/**
 * Created by tangz on 10/4/2015.
 */
public final class FilePredicates {

    public static Predicate<String> forIsFile() {
        return new IsFilePredicate();
    }

    public static Predicate<String> forAllowedFileTypes(String... fileTypes) {
        return new FileTypePredicate(fileTypes);
    }
}
