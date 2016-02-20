package com.jimtang.saver.executor;

import com.jimtang.saver.settings.FileSource;
import com.jimtang.saver.settings.FileTarget;
import com.jimtang.saver.settings.HistorySupplier;
import com.jimtang.saver.history.SaveHistory;
import com.jimtang.saver.util.ArgumentChecker;

import java.io.*;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by tangz on 10/1/2015.
 */
public class StaticURLSaveExecutor
        implements HistorySupplier, FileSource, FileTarget {

    private static final int BUFFER_SIZE = 3000;

    private static final Logger LOGGER = Logger.getLogger(StaticURLSaveExecutor.class.getName());

    private String imageUrl;
    private String saveLocation;
    private int bytes = BUFFER_SIZE;
    private SaveHistory saveHistory = new SaveHistory();

    protected void doSaveWithURL(URL url, String saveLocation) throws IOException {
        try (InputStream in = url.openStream();
             OutputStream out = new FileOutputStream(saveLocation))
        {
            byte[] buf = new byte[bytes];
            int len;
            while ((len = in.read(buf)) >= 0) {
                out.write(buf, 0, len);
            }
            out.flush();
            LOGGER.info(String.format("Wrote file from: %s, to: %s", imageUrl, saveLocation));
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public void doSave() {
        try {
            ArgumentChecker.checkNotNull(imageUrl, saveLocation);
            doSaveWithURL(new URL(imageUrl), saveLocation);
            pushToHistory(new File(saveLocation));

        } catch (IOException e) {
            throw new ImageRetrievalException(e);
        }
    }

    @Override
    public SaveHistory getHistory() {
        return saveHistory;
    }

    @Override
    public void setSourceFile(String urlToSave) {
        this.imageUrl = urlToSave;
    }

    @Override
    public String getSourceFile() {
        return imageUrl;
    }

    @Override
    public void setTargetFile(String saveLocation) {
        this.saveLocation = saveLocation;
    }

    @Override
    public String getTargetFile() {
        return saveLocation;
    }
}
