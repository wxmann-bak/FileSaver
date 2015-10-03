package com.jimtang.saver.plugins.nasamsfc;

/**
 * Created by tangz on 10/2/2015.
 */
public class NasaMSFCOptions {

    static class Option<T> {
        T repr;
        Option(T repr) {
            this.repr = repr;
        }

        public T get() {
            return repr;
        }
    }

    public static class MapType extends Option<String> {

        public static final MapType STANDARD = new MapType("standard");
        public static final MapType COUNTY = new MapType("county");
        public static final MapType LATLON = new MapType("latlon");
        public static final MapType NONE = new MapType("none");

        MapType(String repr) {
            super(repr);
        }
    }

    public static class SatType extends Option<String> {

        public static final SatType IR = new SatType("ir");
        public static final SatType VIS = new SatType("vis");
        public static final SatType WV = new SatType("wv");

        SatType(String repr) {
            super(repr);
        }
    }

    public static class Zoom extends Option<Integer> {

        public static final Zoom HIGH = new Zoom(1);
        public static final Zoom MEDIUM = new Zoom(2);
        public static final Zoom LOW = new Zoom(4);

        Zoom(Integer repr) {
            super(repr);
        }
    }

    public static class Sector extends Option<String> {

        public static final Sector GOES_E_FULL = new Sector("GOES-E FULL");
        public static final Sector GOES_E_CONUS = new Sector("GOES-E CONUS");
        public static final Sector GOES_E_NH = new Sector("GOES-E NHE");
        public static final Sector GOES_E_HURRICANE = new Sector("GOES-E HURRICANE");
        public static final Sector PACIFIC_US = new Sector("GOES-W PACUS");
        public static final Sector PACIFIC = new Sector("GOES-10");

        Sector(String repr) {
            super(repr);
        }
    }
}
