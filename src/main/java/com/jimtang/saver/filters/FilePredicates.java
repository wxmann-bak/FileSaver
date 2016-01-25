package com.jimtang.saver.filters;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Created by tangz on 10/4/2015.
 */
public final class FilePredicates {

    private FilePredicates() {
    }

    public static Predicate<String> forIsFile() {
        return new IsFilePredicate();
    }

    public static Predicate<String> forAllowedFileTypes(String... fileTypes) {
        return new FileTypePredicate(fileTypes);
    }

    public static Predicate<String> forContainingText(final String textSequence) {
        return str -> str.contains(textSequence);
    }
}
