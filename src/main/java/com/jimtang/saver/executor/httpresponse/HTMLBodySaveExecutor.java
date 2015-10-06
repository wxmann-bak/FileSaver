package com.jimtang.saver.executor.httpresponse;

import com.jimtang.saver.executor.HTTPResponseSaveExecutor;
import com.jimtang.saver.executor.ImageRetrievalException;
import com.jimtang.saver.executor.StaticURLSaveExecutor;
import com.jimtang.saver.history.HistorySupplier;
import com.jimtang.saver.history.SaveHistory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by tangz on 10/3/2015.
 */
public abstract class HTMLBodySaveExecutor extends HTTPResponseSaveExecutor implements HostRetrievable, HistorySupplier {

    private SaveHistory history = new SaveHistory();

    @Override
    protected void saveFromResponse(HttpResponse response, String saveLocation) {
        HttpEntity entity = response.getEntity();
        try {
            String htmlStr = EntityUtils.toString(entity);
            String imageUrl = getImageUrlFromHtml(htmlStr);
            StaticURLSaveExecutor saveDelegate = new StaticURLSaveExecutor(imageUrl);
            saveDelegate.doSave(saveLocation);

            pushToHistory(new File(imageUrl));

        } catch (IOException e) {
            throw new ImageRetrievalException(e);
        }
    }

    private String getImageUrlFromHtml(String html) {
        Document document = Jsoup.parse(html);
        Elements imageTags = document.getElementsByTag("IMG");
        return "http://" + getHost() + imageTags.attr("SRC");
    }

    @Override
    public abstract String getHost();

    @Override
    public void pushToHistory(File savedFile) {
        history.add(savedFile);
    }

    @Override
    public SaveHistory getHistory() {
        return history;
    }
}
