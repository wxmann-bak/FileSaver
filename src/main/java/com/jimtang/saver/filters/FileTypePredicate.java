package com.jimtang.saver.filters;

import com.google.common.io.Files;

import java.util.function.Predicate;

/**
 * Created by tangz on 10/4/2015.
 */
class FileTypePredicate implements Predicate<String> {

    private String[] fileExtensions;

    FileTypePredicate(String... fileExtensions) {
        this.fileExtensions = fileExtensions;
    }

    @Override
    public boolean test(String fileString) {
        String ext = Files.getFileExtension(fileString);
        for (String fileExtension: fileExtensions) {
            if (ext.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }
}
