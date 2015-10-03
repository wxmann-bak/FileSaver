package com.jimtang.saver.executor.httpresponse;

import com.jimtang.saver.executor.HTTPResponseSaveExecutor;
import com.jimtang.saver.executor.ImageRetrievalException;
import com.jimtang.saver.executor.StaticURLSaveExecutor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;

/**
 * Created by tangz on 10/3/2015.
 */
public abstract class HTMLBodySaveExecutor extends HTTPResponseSaveExecutor implements HostRetrievable {
    @Override
    protected void saveFromResponse(HttpResponse response, String saveLocation) {
        HttpEntity entity = response.getEntity();
        try {
            String htmlStr = EntityUtils.toString(entity);
            String imageUrl = getImageUrlFromHtml(htmlStr);
            StaticURLSaveExecutor urlSaveExecutor = new StaticURLSaveExecutor(imageUrl);
            urlSaveExecutor.doSave(saveLocation);
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
    protected abstract URI getURI();

    @Override
    public abstract String getHost();
}
