package com.jimtang.saver.filters.time;

/**
 * Created by tangz on 10/4/2015.
 */
public class TimeExtractionException extends RuntimeException {
    public TimeExtractionException() {
        super();
    }

    public TimeExtractionException(String message) {
        super(message);
    }

    public TimeExtractionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeExtractionException(Throwable cause) {
        super(cause);
    }

    protected TimeExtractionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
