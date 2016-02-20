package com.jimtang.saver.executor;

import com.google.common.base.Preconditions;
import com.jimtang.saver.settings.DynamicURLSource;
import com.jimtang.saver.settings.FileTarget;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;

/**
 * Created by tangz on 10/2/2015.
 */
public abstract class HTTPResponseSaveExecutor
        implements DynamicURLSource, FileTarget {

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
    private String saveLocation;

    protected abstract void saveFromResponse(HttpResponse response, String saveLocation);

    public final void doSave() {
        Preconditions.checkNotNull(saveLocation, "Target must not be null");

        HttpGet httpGet = new HttpGet(getURI());
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(httpGet)){
            int code = response.getStatusLine().getStatusCode();
            if (code != 200) {
                String reason = response.getStatusLine().getReasonPhrase();
                throw new ImageRetrievalException(String.format("Expected 200 status. Got instead: %s, with reason: %s", code, reason));
            }
            saveFromResponse(response, saveLocation);

        } catch (IOException e) {
            throw new ImageRetrievalException(e);
        }
    }

    @Override
    public void setTargetFile(String saveLocation) {
        this.saveLocation= saveLocation;
    }

    @Override
    public String getTargetFile() {
        return this.saveLocation;
    }
}
