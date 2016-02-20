package com.jimtang.saver.files;

import com.google.common.base.Strings;
import com.google.common.io.Files;

import java.util.function.Predicate;

/**
 * Created by tangz on 10/4/2015.
 */
public final class FilePredicates {

    private FilePredicates() {
    }

    public static Predicate<String> forIsFile() {
        return fileStr -> !Strings.isNullOrEmpty(Files.getFileExtension(fileStr));
    }

    public static Predicate<String> forAllowedFileTypes(String... fileTypes) {
        return fileString -> {
            String ext = Files.getFileExtension(fileString);
            if (ext != null) {
                for (String fileExtension : fileTypes) {
                    if (ext.equalsIgnoreCase(fileExtension)) {
                        return true;
                    }
                }
            }
            return false;
        };
    }

    public static Predicate<String> forContainingText(final String textSequence) {
        return str -> str.contains(textSequence);
    }
}
