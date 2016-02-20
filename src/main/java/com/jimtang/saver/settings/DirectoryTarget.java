package com.jimtang.saver.settings;

import com.jimtang.saver.executor.SaveExecutor;

/**
 * Created by tangz on 2/20/2016.
 */
public interface DirectoryTarget extends SaveExecutor {
    void setTargetDirectory(String saveLocation);
    String getTargetDirectory();
}
