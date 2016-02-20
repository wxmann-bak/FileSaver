package com.jimtang.saver.executor;

import com.jimtang.saver.time.*;
import com.jimtang.saver.settings.HistorySupplier;
import com.jimtang.saver.history.SaveHistory;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Created by tangz on 1/23/2016.
 */
public class TimeAwareDirectorySaveExecutor extends DirectoryListingSaveExecutor
        implements HistorySupplier {

    private static final Logger LOGGER = Logger.getLogger(TimeAwareDirectorySaveExecutor.class.getName());

    private TimeExtractor timeExtractor;
    private Predicate<String> timeRange = x -> true;
    private Duration intervalLowerBound = Duration.ZERO;
    private SaveHistory saveHistory = new SaveHistory();

    public TimeAwareDirectorySaveExecutor(String onlineDirectory, TimeExtractor timeExtractor) {
        super(onlineDirectory);
        this.timeExtractor = timeExtractor;
    }

    @Override
    protected Predicate<String> getFilter() {
        return super.getFilter().and(fileName -> timeExtractor.canExtractTime(fileName)).and(timeRange);
    }

    public void useRange(LocalDateTime lowerBound, LocalDateTime upperBound) throws SaveConfigurationException {
        if (lowerBound == null && upperBound == null) {
            return;
        }
        if (lowerBound == null) {
            timeRange = fileName -> testTime(TimePredicates.before(upperBound), fileName);
        } else if (upperBound == null) {
            timeRange = fileName -> testTime(TimePredicates.after(lowerBound), fileName);
        } else {
            if (lowerBound.isAfter(upperBound)) {
                throw new SaveConfigurationException("Upper Bound < Lower bound");
            }
            timeRange = fileName -> testTime(TimePredicates.between(lowerBound, upperBound), fileName);
        }
    }

    private boolean testTime(Predicate<LocalDateTime> timePredicate, String fileName) {
        if (!timeExtractor.canExtractTime(fileName)) {
            throw new TimeExtractionException("Can't extract time from: " + fileName);
        }
        return timePredicate.test(timeExtractor.extractDateTime(fileName));
    }

    public void useInterval(Duration intervalLowerBound) {
        this.intervalLowerBound = intervalLowerBound;
    }

    @Override
    protected void saveOne(String url, String saveLocation) {
        if (!saveHistory.isEmpty()) {
            File latestSaved = saveHistory.latest();
            String latestSavedUrl = latestSaved.getPath();
            if (!timeExtractor.canExtractTime(latestSavedUrl) || !timeExtractor.canExtractTime(url)) {
                throw new TimeExtractionException("Can't extract time from: " + latestSavedUrl);
            }
            LocalDateTime latestSavedTime = timeExtractor.extractDateTime(latestSavedUrl);
            LocalDateTime thisUrlSavedTime = timeExtractor.extractDateTime(url);
            if (Duration.between(latestSavedTime, thisUrlSavedTime).compareTo(intervalLowerBound) < 0) {
                LOGGER.info("Skipped file because interval to last saved file is insufficient: " + url);
                return;
            }
        }
        super.saveOne(url, saveLocation);
        pushToHistory(new File(url));
    }

    @Override
    public SaveHistory getHistory() {
        return saveHistory;
    }
}
