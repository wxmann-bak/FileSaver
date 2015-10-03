package com.jimtang.saver.controller;

import com.jimtang.saver.executor.SaveExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by tangz on 10/1/2015.
 */
public abstract class PeriodicSaverController {

    private Long period;
    private TimeUnit timeUnit;

    abstract SaveExecutor getExecutor();

    public void initiateSave() {
        Collection<SaveExecutor> executors = new ArrayList<>();
    }
}
