package com.jimtang.saver.executor;

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
public abstract class HTTPResponseSaveExecutor implements SaveExecutor {

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

    protected abstract void saveFromResponse(HttpResponse response, String saveLocation);

    protected abstract URI getURI();

    private void closeResources(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final void doSave(String saveLocation) {
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(getURI());
            response = HTTP_CLIENT.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();

            if (code != 200) {
                String reason = response.getStatusLine().getReasonPhrase();
                throw new ImageRetrievalException(String.format("Expected 200 status. Got instead: %s, with reason: %s", code, reason));
            }

            saveFromResponse(response, saveLocation);

        } catch (IOException e) {
            throw new ImageRetrievalException(e);
        } finally {
            closeResources(response);
        }
    }
}
