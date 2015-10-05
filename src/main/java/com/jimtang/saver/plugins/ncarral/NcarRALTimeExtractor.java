package com.jimtang.saver.plugins.ncarral;

import com.jimtang.saver.filters.time.RegexTimeExtractor;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Created by tangz on 10/4/2015.
 */
public class NcarRALTimeExtractor extends RegexTimeExtractor {

    private static final NcarRALTimeExtractor INSTANCE = new NcarRALTimeExtractor();

    public static NcarRALTimeExtractor getInstance() {
        return INSTANCE;
    }

    private NcarRALTimeExtractor() {
        // suppress external instantiation
    }

    @Override
    protected Pattern getRegexExp() {
        return Pattern.compile("\\d{8}_\\d{6}");
    }

    @Override
    protected DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    }
}
