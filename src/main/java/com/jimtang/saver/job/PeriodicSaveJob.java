package com.jimtang.saver.job;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by tangz on 10/1/2015.
 */
public class PeriodicSaveJob implements Job {

    private long initialDelay = 0L;
    private long period;
    private TimeUnit timeUnit;
    private SaveRunnable runner;
    private ScheduledExecutorService executorService;

    public PeriodicSaveJob(long period, TimeUnit timeUnit, SaveRunnable runner) {
        this.period = period;
        this.timeUnit = timeUnit;
        this.runner = runner;
    }

    @Override
    public void start() {
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(runner, initialDelay, period, timeUnit);
    }

    @Override
    public void stop() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }
}
