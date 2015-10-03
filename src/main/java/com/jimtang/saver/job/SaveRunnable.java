package com.jimtang.saver.job;

import com.jimtang.saver.executor.SaveExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangz on 10/1/2015.
 */
public final class SaveRunnable implements Runnable {

    private Map<SaveExecutor, String> saveExecutors = new HashMap<>();

    public SaveRunnable() {
    }

    public void addExecutor(SaveExecutor executor, String saveLocation) {
        saveExecutors.put(executor, saveLocation);
    }

    @Override
    public void run() {
        for (Map.Entry<SaveExecutor, String> entry : saveExecutors.entrySet()) {
            SaveExecutor executor = entry.getKey();
            executor.doSave(entry.getValue());
        }
    }
}
