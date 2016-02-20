package com.jimtang.saver.settings;

import com.jimtang.saver.history.SaveHistory;

import java.io.File;

/**
 * Created by tangz on 10/6/2015.
 */
public interface HistorySupplier {

    default void pushToHistory(File savedFile) {
        getHistory().add(savedFile);
    }

    SaveHistory getHistory();
}
