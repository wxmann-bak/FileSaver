package com.jimtang.saver.settings;

import com.jimtang.saver.executor.SaveExecutor;

/**
 * Created by tangz on 2/20/2016.
 */
public interface DirectorySource {
    void setSourceDirectory(String urlToSave);
    String getSourceDirectory();
}
