package com.jimtang.saver.executor.httpresponse;

import com.jimtang.saver.executor.HTTPResponseSaveExecutor;
import com.jimtang.saver.executor.ImageRetrievalException;
import com.jimtang.saver.executor.StaticURLSaveExecutor;
import com.jimtang.saver.history.SaveHistory;
import com.jimtang.saver.settings.HistorySupplier;
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
public abstract class HTMLBodySaveExecutor extends HTTPResponseSaveExecutor
        implements HistorySupplier {

    private SaveHistory history = new SaveHistory();

    @Override
    protected void saveFromResponse(HttpResponse response, String saveLocation) {
        HttpEntity entity = response.getEntity();
        try {
            String htmlStr = EntityUtils.toString(entity);
            String imageUrl = getImageUrlFromHtml(htmlStr);
            StaticURLSaveExecutor saveDelegate = new StaticURLSaveExecutor();
            saveDelegate.setSourceFile(imageUrl);
            saveDelegate.setTargetFile(saveLocation);
            saveDelegate.doSave();

            pushToHistory(new File(saveLocation));

        } catch (IOException e) {
            throw new ImageRetrievalException(e);
        }
    }

    protected String getImageUrlFromHtml(String html) {
        Document document = Jsoup.parse(html);
        Elements imageTags = document.getElementsByTag("IMG");
        if (imageTags.isEmpty()) {
            throw new HTMLTraverseException("No IMG tags found in response body.");
        }
        if (!imageTags.hasAttr("SRC")) {
            throw new HTMLTraverseException("No IMG SRC attribute found in response body.");
        }
        return "http://" + getHost() + imageTags.attr("SRC");
    }

    @Override
    public abstract String getHost();

    @Override
    public SaveHistory getHistory() {
        return history;
    }
}
