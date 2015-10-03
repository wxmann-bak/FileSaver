package com.jimtang.saver.plugins.ncarral;

import com.jimtang.saver.plugins.AbstractInputOption;
import com.jimtang.saver.plugins.PluginInputOption;

/**
 * Created by tangz on 10/3/2015.
 */
public class NcarRALRadarOptions {

    public static class Product extends AbstractInputOption<String> {

        public static final Product BASE_REF_1 = new Product("bref1");
        public static final Product BASE_VEL_1 = new Product("vel1");
        public static final Product REGIONAL_REF = new Product("n0r");

        Product(String repr) {
            super(repr);
        }
    }

    public static class Background extends AbstractInputOption<String> {

        public static final Background BLACK = new Background("black");
        public static final Background GRAY_TERRAIN = new Background("gray");
        public static final Background COLOR_TERRAIN = new Background("color");

        public Background(String repr) {
            super(repr);
        }
    }


}
