package com.jimtang.saver.executor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by tangz on 10/1/2015.
 */
public class StaticURLSaveExecutor implements SaveExecutor {

    public static final int DEFAULT_BYTES = 3000;
    private static final Logger LOGGER = Logger.getLogger(StaticURLSaveExecutor.class.getName());

    private String imageUrl;
    private int bytes = DEFAULT_BYTES;

    public StaticURLSaveExecutor(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public void doSave(String saveLocation) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(imageUrl);
            inputStream = url.openStream();
            outputStream = new FileOutputStream(saveLocation);

            byte[] byteArray = new byte[bytes];
            int len;
            while ((len = inputStream.read(byteArray)) != -1) {
                outputStream.write(byteArray, 0, len);
            }
            LOGGER.info(String.format("Wrote file from: %s, to: %s", imageUrl, saveLocation));
        } catch (IOException e) {
            throw new ImageRetrievalException(e);
        } finally {
            try {
                if (inputStream != null)  inputStream.close();
                if (outputStream != null)   outputStream.close();
            } catch (IOException e) {
                LOGGER.severe("Issue in closing stream resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
