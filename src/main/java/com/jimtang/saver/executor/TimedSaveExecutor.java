package com.jimtang.saver.executor;

import com.jimtang.saver.files.FileNameHandler;
import com.jimtang.saver.settings.DynamicFileTarget;
import com.jimtang.saver.settings.FileSource;
import com.jimtang.saver.settings.FileTarget;
import com.jimtang.saver.settings.HistorySupplier;
import com.jimtang.saver.history.SaveHistory;
import com.jimtang.saver.files.TimestampAppender;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Created by tangz on 10/1/2015.
 */
public class TimedSaveExecutor implements DynamicFileTarget {

    private static final Logger LOGGER = Logger.getLogger(TimedSaveExecutor.class.getName());

    private FileTarget primaryExecutor;
    private TimestampAppender timestampAppender;
    private boolean conservativeWrite;
    private String saveLocation;

    public TimedSaveExecutor(FileTarget primaryExecutor) {
        this(primaryExecutor, false);
    }

    public TimedSaveExecutor(FileTarget primaryExecutor, boolean conservativeWrite) {
        if (primaryExecutor instanceof TimedSaveExecutor) {
            throw new IllegalArgumentException("Can't use a timed executor with another timed executor");
        }
        this.primaryExecutor = primaryExecutor;
        this.timestampAppender = new TimestampAppender("yyyyMMdd_HHmmss", "UTC");
        this.conservativeWrite = conservativeWrite;
    }

    @Override
    public void doSave() {
        primaryExecutor.setTargetFile(getTargetFile());
        primaryExecutor.doSave();
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
//                                    history.removeLatest();
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

    @Override
    public FileNameHandler getFileNameHandler() {
        return this.timestampAppender;
    }

    @Override
    public void setTargetFile(String saveLocation) {
        primaryExecutor.setTargetFile(saveLocation);
    }

    @Override
    public String getTargetFile() {
        return timestampAppender.buildName(primaryExecutor.getTargetFile());
    }
}
