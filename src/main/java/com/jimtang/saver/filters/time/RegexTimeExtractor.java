package com.jimtang.saver.filters.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tangz on 10/4/2015.
 */
public abstract class RegexTimeExtractor implements TimeExtractor {

    protected abstract Pattern getRegexExp();

    protected abstract DateTimeFormatter getDateTimeFormatter();

    @Override
    public LocalDateTime extractDateTime(String fileName) {
        Matcher matcher = getRegexExp().matcher(fileName);

        if (matcher.find()) {
            String dateTimeString = matcher.group(0);
            return LocalDateTime.parse(dateTimeString, getDateTimeFormatter());
        }
        throw new TimeExtractionException("Cannot extract date time from file name!");
    }
}
