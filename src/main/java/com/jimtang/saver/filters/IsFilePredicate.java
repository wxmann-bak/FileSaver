package com.jimtang.saver.filters;

import com.google.common.base.Strings;
import com.google.common.io.Files;

import java.util.function.Predicate;

/**
 * Created by tangz on 10/4/2015.
 */
class IsFilePredicate implements Predicate<String> {

    @Override
    public boolean test(String fileString) {
        String ext = Files.getFileExtension(fileString);
        return !Strings.isNullOrEmpty(ext);
    }
}
