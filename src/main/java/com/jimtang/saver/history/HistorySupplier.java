package com.jimtang.saver.history;

import java.io.File;

/**
 * Created by tangz on 10/6/2015.
 */
public interface HistorySupplier {

    void pushToHistory(File savedFile);

    SaveHistory getHistory();
}
