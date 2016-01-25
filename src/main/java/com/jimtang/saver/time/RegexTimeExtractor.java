package com.jimtang.saver.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tangz on 10/4/2015.
 */
public abstract class RegexTimeExtractor implements TimeExtractor {

    private final Pattern pattern = Pattern.compile(getRegexExp());
    private Matcher matcher;

    protected abstract String getRegexExp();

    public abstract DateTimeFormatter getFormatter();

    @Override
    public boolean canExtractTime(String fileName) {
        matcher = pattern.matcher(fileName);
        return matcher.find();
    }

    @Override
    public LocalDateTime extractDateTime(String fileName) {
        if (!canExtractTime(fileName)) {
            return null;
        }
        if (matcher == null) {
            matcher = pattern.matcher(fileName);
            matcher.find();
        }
        String dateTimeString = matcher.group(0);
        return LocalDateTime.parse(dateTimeString, getFormatter());
    }
}
