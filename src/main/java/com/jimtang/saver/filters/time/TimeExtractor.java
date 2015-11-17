package com.jimtang.saver.filters.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by tangz on 10/4/2015.
 */
public interface TimeExtractor {

    LocalDateTime extractDateTime(String fileName);
}
