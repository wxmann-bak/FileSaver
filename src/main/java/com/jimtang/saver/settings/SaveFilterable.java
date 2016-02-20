package com.jimtang.saver.settings;

import java.util.function.Predicate;

/**
 * Created by tangz on 10/4/2015.
 */
public interface SaveFilterable {

    void addFilter(Predicate<String> filter);
}
