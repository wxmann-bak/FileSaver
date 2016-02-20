package com.jimtang.saver.settings;

import com.jimtang.saver.executor.SaveExecutor;
import com.jimtang.saver.files.FileNameHandler;

/**
 * Created by tangz on 2/20/2016.
 */
public interface DynamicFileTarget extends FileTarget {
    FileNameHandler getFileNameHandler();
}
