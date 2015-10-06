package com.jimtang.saver.executor;

import com.jimtang.saver.history.HistorySupplier;
import com.jimtang.saver.history.SaveHistory;
import com.jimtang.saver.util.TimestampAppender;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Created by tangz on 10/1/2015.
 */
public class TimedSaveExecutor implements SaveExecutor {

    private static final Logger LOGGER = Logger.getLogger(TimedSaveExecutor.class.getName());

    private SaveExecutor primaryExecutor;
    private TimestampAppender timestampAppender;
    private boolean conservativeWrite;

    public TimedSaveExecutor(SaveExecutor primaryExecutor) {
        this(primaryExecutor, false);
    }

    public TimedSaveExecutor(SaveExecutor primaryExecutor, boolean conservativeWrite) {
        if (primaryExecutor instanceof TimedSaveExecutor) {
            throw new IllegalArgumentException("Can't use a timed executor with another timed executor");
        }
        this.primaryExecutor = primaryExecutor;
        this.timestampAppender = new TimestampAppender("yyyyMMdd_HHmmss", "UTC");
        this.conservativeWrite = conservativeWrite;
    }

    @Override
    public void doSave(String saveLocation) {
        String timeAppendedLoc = timestampAppender.buildName(saveLocation);
        primaryExecutor.doSave(timeAppendedLoc);
        deleteDupIfConservativeWrite();
    }

    private void deleteDupIfConservativeWrite() {
        if (conservativeWrite) {
            if (primaryExecutor instanceof HistorySupplier) {
                try {
                    HistorySupplier historySupplier = (HistorySupplier) primaryExecutor;
                    SaveHistory history = historySupplier.getHistory();
                    Iterator<File> iter = history.iterator();
                    int i = 0;
                    File latest = null;
                    while (iter.hasNext()) {
                        if (i == 0) {
                            latest = iter.next();
                        } else {
                            File file = iter.next();
                            if (latest != null && FileUtils.contentEquals(file, latest)) {
                                if (!latest.delete()) {
                                    LOGGER.warning("Can't delete a duplicate file " + latest.getPath());
                                } else {
                                    // remove the deleted file from the history; we're good.
                                    LOGGER.info(String.format(
                                            "Removed duplicate file: %s, of already saved file: %s",
                                            latest.getPath(), file.getPath()));
                                    history.removeLatest();
                                }
                                return;
                            }
                        }
                        i++;
                    }
                } catch (IOException e) {
                    LOGGER.warning("Could not close stream resources");
                    e.printStackTrace();
                }
            } else {
                LOGGER.warning(
                        "This implementation of save execution does not keep history so we cannot get rid of duplicates");
            }
        }
    }
}
