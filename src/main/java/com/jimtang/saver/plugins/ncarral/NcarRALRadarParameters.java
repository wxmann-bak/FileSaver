package com.jimtang.saver.plugins.ncarral;

import java.util.Date;

/**
 * Created by tangz on 10/3/2015.
 */
public class NcarRALRadarParameters {

    private String icao;
    private String product;
    private String background;
    private Date endDate;
    private int endTime = -1;
    private int duration = 0;

    public NcarRALRadarParameters(String icao, String product, String background, Date endDate) {
        this.icao = icao;
        this.product = product;
        this.background = background;
        this.endDate = endDate;
    }

    public String getIcao() {
        return icao;
    }

    public String getProduct() {
        return product;
    }

    public String getBackground() {
        return background;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getDuration() {
        return duration;
    }
}
