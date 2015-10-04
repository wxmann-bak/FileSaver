package com.jimtang.saver.filters;

/**
 * Created by tangz on 10/4/2015.
 */
public interface FileTypeFilterable extends SaveFilterable {

    void setAllowedFileTypes(String... fileTypes);
}
