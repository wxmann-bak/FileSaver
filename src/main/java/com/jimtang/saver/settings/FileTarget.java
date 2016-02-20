package com.jimtang.saver.settings;

import com.jimtang.saver.executor.SaveExecutor;

/**
 * Created by tangz on 2/20/2016.
 */
public interface FileTarget extends SaveExecutor {
    void setTargetFile(String saveLocation);
    String getTargetFile();
}
