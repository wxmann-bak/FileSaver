package com.jimtang.saver.plugins.nasamsfc;

import com.jimtang.saver.executor.httpresponse.HTMLBodySaveExecutor;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by tangz on 10/2/2015.
 */
public class NasaMSFCSaveExecutor extends HTMLBodySaveExecutor {

    private static final String HOST = "weather.msfc.nasa.gov";

    private NasaMSFCParameters parameters;

    public NasaMSFCSaveExecutor(NasaMSFCParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public URI getURI() {
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

    @Override
    public String getHost() {
        return HOST;
    }
}
