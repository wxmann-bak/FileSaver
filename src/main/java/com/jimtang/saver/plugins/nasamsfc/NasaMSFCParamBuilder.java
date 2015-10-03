package com.jimtang.saver.plugins.nasamsfc;

/**
 * Created by tangz on 10/3/2015.
 */
public class NasaMSFCParamBuilder {

    // plenty of options. See http://weather.msfc.nasa.gov/GOES/getsatellite.html
    private String satelliteSector;

    // position coordinates. Prefer x and y
    private int x = 0;
    private int y = 0;

    // default "standard", can be set to "county", "latlon", "none"
    private String map = "standard";

    // default 1 (high), can be set to 2 (med), or 4 (low)
    private int zoom = 1;

    // default ir, can be set to "vis" or "wv"
    private String info = "ir";

    // ir[1-10].pal or wv[1-6].pal; 1's are default
    private String palette = "ir1.pal";

    // default dimensions: 1200x800
    private int height = 1200;
    private int width = 800;

    public NasaMSFCParamBuilder() {
    }

    public NasaMSFCParamBuilder withSatelliteSector(NasaMSFCOptions.Sector sector) {
        this.satelliteSector = sector.get();
        return this;
    }

    public NasaMSFCParamBuilder withPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public NasaMSFCParamBuilder withMapType(NasaMSFCOptions.MapType mapType) {
        this.map = mapType.get();
        return this;
    }

    public NasaMSFCParamBuilder withZoom(NasaMSFCOptions.Zoom zoom1) {
        this.zoom = zoom1.get();
        return this;
    }

    public NasaMSFCParamBuilder withSatType(NasaMSFCOptions.SatType satType) {
        this.info = satType.get();
        return this;
    }

    public NasaMSFCParamBuilder withPalette(String palette) {
        this.palette = palette;
        return this;
    }

    public NasaMSFCParamBuilder withDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public NasaMSFCParameters build() {
        if (satelliteSector == null) {
            throw new IllegalArgumentException("Satellite section has to be set");
        }
        return new NasaMSFCParameters(satelliteSector, x, y, map, zoom, info, palette, height, width);
    }
}
