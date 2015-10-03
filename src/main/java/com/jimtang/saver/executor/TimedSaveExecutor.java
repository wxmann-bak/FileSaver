package com.jimtang.saver.executor;

import com.jimtang.saver.util.TimestampAppender;

/**
 * Created by tangz on 10/1/2015.
 */
public class TimedSaveExecutor implements SaveExecutor {

    private SaveExecutor primarySaveExecutor;
    private TimestampAppender timestampAppender;

    public TimedSaveExecutor(SaveExecutor primarySaveExecutor) {
        if (primarySaveExecutor instanceof TimedSaveExecutor) {
            throw new IllegalArgumentException("Can't use a timed executor with another timed executor");
        }
        this.primarySaveExecutor = primarySaveExecutor;
        this.timestampAppender = new TimestampAppender("yyyyMMdd_HHmmss", "UTC");
    }

    @Override
    public void doSave(String saveLocation) {
        String timeAppendedLoc = timestampAppender.buildName(saveLocation);
        primarySaveExecutor.doSave(timeAppendedLoc);
    }
}
