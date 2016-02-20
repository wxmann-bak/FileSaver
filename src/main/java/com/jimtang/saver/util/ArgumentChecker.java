package com.jimtang.saver.util;

import com.google.common.base.Preconditions;

/**
 * Created by tangz on 2/20/2016.
 */
public class ArgumentChecker {

    public static void checkNotNull(String source, String target) {
        Preconditions.checkNotNull(source, "Source location must not be null");
        Preconditions.checkNotNull(target, "Target location must not be null");
    }
}
