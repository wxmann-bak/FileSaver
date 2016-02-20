package com.jimtang.saver.files;

import com.jimtang.saver.filters.SaveFilterable;

/**
 * Created by tangz on 10/4/2015.
 */
public interface FileTypeFilterable extends SaveFilterable {

    void setAllowedFileTypes(String... fileTypes);
}
