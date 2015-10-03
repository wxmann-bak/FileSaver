package com.jimtang.saver.plugins.ncarral;

import com.jimtang.saver.executor.httpresponse.HTMLBodySaveExecutor;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

/**
 * Created by tangz on 10/3/2015.
 */
public class NcarRALRadarSaveExecutor extends HTMLBodySaveExecutor {

    public static final String HOST = "weather.rap.ucar.edu/radar/";

    private NcarRALRadarParameters parameters;

    public NcarRALRadarSaveExecutor(NcarRALRadarParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public String getHost() {
        return HOST;
    }

    @Override
    protected URI getURI() {
        try {
            String endDateStr = new SimpleDateFormat("yyyyMMdd").format(parameters.getEndDate());
            return new URIBuilder()
                    .setScheme("http")
                    .setHost(HOST)
                    .setPath("/displayRad.php")
                    .setParameter("icao", parameters.getIcao())
                    .setParameter("prod", parameters.getProduct())
                    .setParameter("bkgr", parameters.getBackground())
                    .setParameter("endDate", endDateStr)
                    .setParameter("endTime", String.valueOf(parameters.getEndTime()))
                    .setParameter("duration", String.valueOf(parameters.getDuration()))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
