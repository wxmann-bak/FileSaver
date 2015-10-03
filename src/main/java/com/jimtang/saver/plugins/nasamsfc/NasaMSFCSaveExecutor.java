package com.jimtang.saver.plugins.nasamsfc;

import com.jimtang.saver.executor.HTTPResponseSaveExecutor;
import com.jimtang.saver.executor.ImageRetrievalException;
import com.jimtang.saver.executor.StaticURLSaveExecutor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by tangz on 10/2/2015.
 */
public class NasaMSFCSaveExecutor extends HTTPResponseSaveExecutor {

    private static final String HOST = "weather.msfc.nasa.gov";

    private NasaMSFCParameters parameters;

    public NasaMSFCSaveExecutor(NasaMSFCParameters parameters) {
        this.parameters = parameters;
    }

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
        return "http://" + HOST + imageTags.attr("SRC");
    }

    @Override
    protected URI getURI() {
        try {
            return new URIBuilder()
                    .setScheme("http")
                    .setHost(HOST)
                    .setPath("/cgi-bin/get-goes")
                    .setParameter("satellite", parameters.getSatelliteSector())
                    .setParameter("x", String.valueOf(parameters.getX()))
                    .setParameter("y", String.valueOf(parameters.getY()))
                    .setParameter("map", parameters.getMap())
                    .setParameter("zoom", String.valueOf(parameters.getZoom()))
                    .setParameter("info", parameters.getInfo())
                    .setParameter("palette", parameters.getPalette())
                    .setParameter("quality", String.valueOf(parameters.getQuality()))
                    .setParameter("width", String.valueOf(parameters.getWidth()))
                    .setParameter("height", String.valueOf(parameters.getHeight()))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
