package com.jimtang.saver.plugins.ncarral;

import java.util.Date;

/**
 * Created by tangz on 10/3/2015.
 */
public class NcarRALRadarParamBuilder {

    private String icao;
    private String product = NcarRALRadarOptions.Product.BASE_REF_1.get();
    private String background = NcarRALRadarOptions.Background.BLACK.get();
    private Date endDate = new Date();

    public NcarRALRadarParamBuilder() {
    }

    public NcarRALRadarParamBuilder withICAO(String icao) {
        this.icao = icao;
        return this;
    }

    public NcarRALRadarParamBuilder withProduct(NcarRALRadarOptions.Product product1) {
        this.product = product1.get();
        return this;
    }

    public NcarRALRadarParamBuilder withBackground(NcarRALRadarOptions.Background bckg) {
        this.background = bckg.get();
        return this;
    }

    public NcarRALRadarParameters build() {
        return new NcarRALRadarParameters(icao, product, background, endDate);
    }
}
