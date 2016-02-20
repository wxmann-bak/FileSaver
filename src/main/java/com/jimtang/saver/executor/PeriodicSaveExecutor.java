package com.jimtang.saver.executor;

import com.google.common.collect.Lists;
import com.jimtang.saver.settings.FileTarget;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by tangz on 2/20/2016.
 */
public class PeriodicSaveExecutor implements SaveExecutor {

    private Collection<SaveGroup> saveGroups = Lists.newArrayList();

    private static class SaveGroup {
        private TimedSaveExecutor saveExecutor;
        private Duration savePeriod;
        private LocalDateTime end;

        private SaveGroup(FileTarget directSaveExecutor, Duration savePeriod,
                          LocalDateTime end, boolean conservativeSave) throws SaveConfigurationException {
            if (end.isBefore(LocalDateTime.now())) {
                throw new SaveConfigurationException("Cannot have ending of the save group be before current!");
            }
            this.saveExecutor = new TimedSaveExecutor(directSaveExecutor, conservativeSave);
            this.savePeriod = savePeriod;
            this.end = end;
        }

        private long periodToMillis() {
            return savePeriod.toMillis();
        }

        private long endToMillis() {
            if (end == null) {
                return 0L;
            }
            Duration duration = Duration.between(LocalDateTime.now(), end);
            return duration.toMillis();
        }

        private Runnable toRunnable() {
            return () -> saveExecutor.doSave();
        }
    }

    public void addGroup(FileTarget directSaveExecutor, Duration savePeriod)
            throws SaveConfigurationException {
        saveGroups.add(new SaveGroup(directSaveExecutor, savePeriod, null, false));
    }

    public void addGroup(FileTarget directSaveExecutor, Duration savePeriod, LocalDateTime until)
            throws SaveConfigurationException {
        saveGroups.add(new SaveGroup(directSaveExecutor, savePeriod, until, false));
    }

    @Override
    public void doSave() {
        Collection<ScheduledFuture<?>> futures = Lists.newArrayList();

        for (SaveGroup saveGroup: saveGroups) {
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(
                    saveGroup.toRunnable(), 0L, saveGroup.periodToMillis(), MILLISECONDS);
            futures.add(scheduledFuture);

            if (saveGroup.end != null) {
                ScheduledFuture<?> terminatingFuture = scheduler.schedule((Runnable) () -> {
                    scheduledFuture.cancel(true);
                    scheduler.shutdownNow();
                }, saveGroup.endToMillis(), MILLISECONDS);
                futures.add(terminatingFuture);
            }
        }

        try {
            for (ScheduledFuture<?> future : futures) {
                future.get();
            }
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
            throw new ImageRetrievalException(e);
        }
    }
}
