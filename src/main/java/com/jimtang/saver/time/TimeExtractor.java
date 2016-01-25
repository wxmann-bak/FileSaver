package com.jimtang.saver.time;

import java.time.LocalDateTime;

/**
 * Created by tangz on 10/4/2015.
 */
public interface TimeExtractor {

    boolean canExtractTime(String fileName);

    LocalDateTime extractDateTime(String fileName);
}
