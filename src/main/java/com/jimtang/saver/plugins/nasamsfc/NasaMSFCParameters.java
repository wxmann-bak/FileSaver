package com.jimtang.saver.plugins.nasamsfc;

/**
 * Created by tangz on 10/2/2015.
 */
public class NasaMSFCParameters {

    private String satelliteSector;
    private int x;
    private int y;
    private String map;
    private int zoom;
    private String info;
    private String palette;
    private int height;
    private int width;

    /** Static parameters **/

    // use 90
    private int quality = 90;

    public NasaMSFCParameters(String satelliteSector, int x, int y, String map, int zoom, String info, String palette, int height, int width)
    {
        this.satelliteSector = satelliteSector;
        this.x = x;
        this.y = y;
        this.map = map;
        this.zoom = zoom;
        this.info = info;
        this.palette = palette;
        this.height = height;
        this.width = width;
    }

    public String getSatelliteSector() {
        return satelliteSector;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getMap() {
        return map;
    }

    public int getZoom() {
        return zoom;
    }

    public String getInfo() {
        return info;
    }

    public String getPalette() {
        return palette;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getQuality() {
        return quality;
    }
}
